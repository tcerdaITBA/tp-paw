package tp.paw.khet.persistence;

import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.OrderCriteria;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.Product.ProductBuilder;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;
import tp.paw.khet.persistence.querybuilder.ProductKeywordQueryBuilder;

@Repository
public class ProductHibernateDao implements ProductDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ProductKeywordQueryBuilder productKeywordQueryBuilder;

	private final String selectProductTemplate;
	private final String selectProductFilterCategoryTemplate;
	private final EnumMap<ProductSortCriteria, ProductSortCriteriaClause> productSortCriteriaClauseMap;
	private final EnumMap<OrderCriteria, OrderCriteriaClause> orderCriteriaClauseMap;

	public ProductHibernateDao() {
		selectProductTemplate = "select p from Product as p ${join} ${group} ORDER BY ${order} ${orderCriteria}";
		selectProductFilterCategoryTemplate = "select p from Product as p ${join} WHERE p.category = :category ${group} ORDER BY ${order} ${orderCriteria}";

		productSortCriteriaClauseMap = new EnumMap<>(ProductSortCriteria.class);
		initProductSortCriteriaClauseMap();
		
		orderCriteriaClauseMap = new EnumMap<>(OrderCriteria.class);
		initOrderCriteriaClauseMap();
	}

	private void initProductSortCriteriaClauseMap() {
		productSortCriteriaClauseMap.put(ProductSortCriteria.ALPHA, new ProductSortCriteriaClause("lower(p.name)"));
		productSortCriteriaClauseMap.put(ProductSortCriteria.DATE, new ProductSortCriteriaClause("p.uploadDate"));
		productSortCriteriaClauseMap.put(ProductSortCriteria.VOTES, new ProductSortCriteriaClause("left join p.votingUsers as vu", "count(vu)", "GROUP BY p"));
	}
	
	private void initOrderCriteriaClauseMap() {
		orderCriteriaClauseMap.put(OrderCriteria.ASC, new OrderCriteriaClause("ASC"));
		orderCriteriaClauseMap.put(OrderCriteria.DESC, new OrderCriteriaClause("DESC"));
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
	public Product createProduct(final String name, final String description, final String shortDescription,
			final String website, final Category category, final Date uploadDate, final byte[] logo, final User creator) {

		final ProductBuilder productBuilder = Product.getBuilder(name, shortDescription);

		productBuilder.description(description).category(category).uploadDate(uploadDate).logo(logo).creator(creator);

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
	public int getTotalProductsByKeyword(final Set<String> keywords) {
		final Map<String, String> keyWordsRegExp = new HashMap<>();
		final String countQuery = productKeywordQueryBuilder.buildCountQuery(keywords, keyWordsRegExp);

		final TypedQuery<Long> query = em.createQuery(countQuery, Long.class);

		for (final Entry<String, String> e : keyWordsRegExp.entrySet())
			query.setParameter(e.getKey(), e.getValue());
		
		return query.getSingleResult().intValue();
	}
	
	@Override
	public List<Product> getPlainProductsByKeyword(final Set<String> keywords, final int offset, final int length) {

		final Map<String, String> keyWordsRegExp = new HashMap<>();
		final String keywordQuery = productKeywordQueryBuilder.buildQuery(keywords, keyWordsRegExp);

		final TypedQuery<Product> query = em.createQuery(keywordQuery, Product.class);

		for (final Entry<String, String> e : keyWordsRegExp.entrySet())
			query.setParameter(e.getKey(), e.getValue());

		return pagedResult(query, offset, length);
	}

	@Override
	public List<Product> getPlainProductsRangeByCategory(final Category category, final ProductSortCriteria sortCriteria, final OrderCriteria orderCriteria, 
			final int offset, final int length) {

		String strQuery = productSortCriteriaClauseMap.get(sortCriteria).injectIntoTemplate(selectProductFilterCategoryTemplate);
		strQuery = orderCriteriaClauseMap.get(orderCriteria).injectIntoTemplate(strQuery);
		
		final TypedQuery<Product> query = em.createQuery(strQuery, Product.class);
		query.setParameter("category", category);

		return pagedResult(query, offset, length);
	}

	@Override
	public List<Product> getPlainProductsRange(final ProductSortCriteria sortCriteria, final OrderCriteria orderCriteria, final int offset, final int length) {
		String strQuery = productSortCriteriaClauseMap.get(sortCriteria).injectIntoTemplate(selectProductTemplate);
		strQuery = orderCriteriaClauseMap.get(orderCriteria).injectIntoTemplate(strQuery);

		final TypedQuery<Product> query = em.createQuery(strQuery, Product.class);

		return pagedResult(query, offset, length);
	}

	private List<Product> pagedResult(final TypedQuery<Product> query, final int offset, final int length) {
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return query.getResultList();
	}

	private static class ProductSortCriteriaClause {
		private final StrSubstitutor substitutor;

		public ProductSortCriteriaClause(final String orderClause) {
			this(StringUtils.EMPTY, orderClause, StringUtils.EMPTY);
		}

		public ProductSortCriteriaClause(final String joinClause, final String orderClause, final String groupClause) {
			final Map<String, String> valuesMap = new HashMap<>();
			valuesMap.put("join", joinClause);
			valuesMap.put("order", orderClause);
			valuesMap.put("group", groupClause);

			substitutor = new StrSubstitutor(valuesMap);
		}

		public String injectIntoTemplate(final String template) {
			return substitutor.replace(template);
		}
	}
	
	private static class OrderCriteriaClause {
		private final StrSubstitutor substitutor;
		
		public OrderCriteriaClause(final String orderCriteriaClause) {
			final Map<String, String> valuesMap = new HashMap<>();

			valuesMap.put("orderCriteria", orderCriteriaClause);
			
			substitutor = new StrSubstitutor(valuesMap);
		}
		
		public String injectIntoTemplate(final String template) {
			return substitutor.replace(template);
		}
		
	}
}
