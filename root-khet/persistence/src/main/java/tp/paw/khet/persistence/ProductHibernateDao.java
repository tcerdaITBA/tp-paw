package tp.paw.khet.persistence;

import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.stereotype.Repository;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.Product.ProductBuilder;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;

@Repository
public class ProductHibernateDao implements ProductDao {
	    	
	@PersistenceContext
	private EntityManager em;
	
	private final String selectProductTemplate;
	private final String selectProductWithCategoryTemplate;
	private final EnumMap<ProductSortCriteria, ProductSortCriteriaClause> productSortCriteriaClauseMap;
		
	public ProductHibernateDao() {
		selectProductTemplate = "select p from Product as p ${join} ${group} ORDER BY ${order}";
		
		selectProductWithCategoryTemplate = "select p from Product as p ${join} WHERE p.category = :category ${group} ORDER BY ${order}";
		
		final Map<String, String> whereCategoryValuesMap = new HashMap<>();
		whereCategoryValuesMap.put("where", "WHERE category = :category");
				
		productSortCriteriaClauseMap = new EnumMap<>(ProductSortCriteria.class);
		initProductSortCriteriaClauseMap();
	}

	private void initProductSortCriteriaClauseMap() {
		productSortCriteriaClauseMap.put(ProductSortCriteria.ALPHABETICALLY, new ProductSortCriteriaClause("lower(p.name)"));
		
		productSortCriteriaClauseMap.put(ProductSortCriteria.MOST_RECENT, new ProductSortCriteriaClause("p.uploadDate DESC"));		
		
		productSortCriteriaClauseMap.put(ProductSortCriteria.POPULARITY, 
				new ProductSortCriteriaClause("left join p.votingUsers as vu", "count(vu) DESC, lower(p.name)", "p"));
	}

	@Override
	public Product getFullProductById(final int productId) {
		final TypedQuery<Product> query = em.createQuery("from Product as p join fetch p.creator as pc where p.id = :productId", Product.class);
		query.setParameter("productId", productId);

		final List<Product> result = query.getResultList();
		
		if (result.isEmpty())
			return null;
		
		final Product product = result.get(0);
		
		// Hibernate lazy initialization
		product.getImages().size();
		product.getVideos().size();
		
		return product;
	}
	
	@Override
	public Product getPlainProductById(final int productId) {
		return em.find(Product.class, productId);
	}
	
	@Override
	public List<Product> getPlainProductsByUserId(final int userId) {
		final TypedQuery<Product> query = em.createQuery("from Product as p where p.creator.userId = :userId ORDER BY p.uploadDate DESC", Product.class);
		query.setParameter("userId", userId);
		
		return query.getResultList();
	}
	
	@Override
	public Product createProduct(final String name, final String description, final String shortDescription, final String website, 
								 final Category category, final Date uploadDate, final byte[] logo, final User creator) {

		final ProductBuilder productBuilder = Product.getBuilder(name, shortDescription);
		
		productBuilder.description(description)
					  .category(category)
					  .uploadDate(uploadDate)
					  .logo(logo)
					  .creator(creator);
		
		if (website != null)
			productBuilder.website(website);
		
		final Product product = productBuilder.build();
		
		em.persist(product);
		
		return product;		
	}

	@Override
	public byte[] getLogoByProductId(final int productId) {
		final Product product = getPlainProductById(productId);

		return product == null ? new byte[0] : product.getLogo();
	}

    @Override
    public int getTotalProducts() {
    	final TypedQuery<Long> query = em.createQuery("select count(*) from Product as p", Long.class);
    	final Long total = query.getSingleResult();
    	
        return total != null ? total.intValue() : 0;
    }

    @Override
    public int getTotalProductsInCategory(final Category category) {
       	final TypedQuery<Long> query = em.createQuery("select count(*) from Product as p where p.category = :category", Long.class);
       	query.setParameter("category", category);
    	final Long total = query.getSingleResult();
    	
        return total != null ? total.intValue() : 0;
    }

	@Override
	public boolean deleteProductById(final int productId) {
		final Product product = getPlainProductById(productId);
		
		if (product != null)
			em.remove(product);
		
		return product == null ? false : true;
	}

	@Override
	public List<Product> getPlainProductsByKeyword(final String keyword, final int maxLength) {
		final String firstWordKeyword = keyword+"%";
		final String otherWordsKeyword = "% "+keyword+"%";
		
		final TypedQuery<Product> query = em.createQuery(
				  "from Product as p where "
				+ "lower(p.name) LIKE lower(:firstWordKeyword) OR lower(p.name) LIKE lower(:otherWordsKeyword) OR "
				+ "lower(p.shortDescription) LIKE lower(:firstWordKeyword) OR lower(p.shortDescription) LIKE lower(:otherWordsKeyword) "
				+ "ORDER BY lower(p.name)", 
				Product.class);
		
		query.setParameter("firstWordKeyword", firstWordKeyword);
		query.setParameter("otherWordsKeyword", otherWordsKeyword);
		query.setMaxResults(maxLength);
		
		return query.getResultList();
	}
	
    private List<Product> pagedResult(final TypedQuery<Product> query, final int offset, final int length) {
    	query.setFirstResult(offset);
    	query.setMaxResults(length);
    	return query.getResultList();
    }

	@Override
	public List<Product> getPlainProductsRangeByCategory(final Category category, final ProductSortCriteria sortCriteria, 
			final int offset, final int length) {
		
		final String strQuery = productSortCriteriaClauseMap.get(sortCriteria).injectIntoTemplate(selectProductWithCategoryTemplate);
		final TypedQuery<Product> query = em.createQuery(strQuery, Product.class);
		query.setParameter("category", category);
		
		return pagedResult(query, offset, length);
	}

	@Override
	public List<Product> getPlainProductsRange(final ProductSortCriteria sortCriteria, final int offset, final int length) {
		final String strQuery = productSortCriteriaClauseMap.get(sortCriteria).injectIntoTemplate(selectProductTemplate);
		final TypedQuery<Product> query = em.createQuery(strQuery, Product.class);
		
		return pagedResult(query, offset, length);
	}
	
	private static class ProductSortCriteriaClause {
		private final Map<String, String> valuesMap;
		private final StrSubstitutor substitutor;

		public ProductSortCriteriaClause(final String orderClause) {
			valuesMap = new HashMap<>();
			valuesMap.put("join", StringUtils.EMPTY);
			valuesMap.put("group", StringUtils.EMPTY);
			valuesMap.put("order", orderClause);
			
			substitutor = new StrSubstitutor(valuesMap);
		}
		
		public ProductSortCriteriaClause(final String joinClause, final String orderClause, final String groupClause) {
			valuesMap = new HashMap<>();
			valuesMap.put("join", joinClause);
			valuesMap.put("order", orderClause);
			valuesMap.put("group", "GROUP BY " + groupClause);
			
			substitutor = new StrSubstitutor(valuesMap);
		}
		
		public String injectIntoTemplate(final String template) {
			return substitutor.replace(template);
		}
	}
}
