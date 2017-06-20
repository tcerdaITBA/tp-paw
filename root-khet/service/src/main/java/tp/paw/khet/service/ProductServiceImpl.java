package tp.paw.khet.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.Product.ProductBuilder;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.persistence.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	private final static int MIN_WORD_SIZE = 3;
	private final static String FIRST_SEARCH_FIELD = "lower(p.name)";
	private final static String SECOND_SEARCH_FIELD = "lower(p.shortDescription)";

	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	@Override
	public Product getFullProductById(final int productId) {
		final Product product = productDao.getFullProductById(productId);
		
		if (product == null)
			return null;
		
		return Product.getBuilderFromProduct(product).commentFamilies(commentService.getCommentsByProductId(productId)).build();		
	}

	@Override
	public Product getPlainProductById(final int productId) {
		return productDao.getPlainProductById(productId);
	}

	@Override
	public List<Product> getPlainProductsByUserId(final int userId) {
		return productDao.getPlainProductsByUserId(userId);
	}
	
	@Transactional
	@Override
	public Product createProduct(final String name, final String description, final String shortDescription, final String website,
			final Category category, final byte[] logo, final int creatorId, final List<byte[]> imageBytes, final List<String> videoIds) {
		
		final Product product = productDao.createProduct(name, description, shortDescription, 
						website, category, new Date(), logo, userService.getUserById(creatorId));
		
		final ProductBuilder productBuilder = Product.getBuilderFromProduct(product);
		
		productBuilder.videos(videoService.createVideos(videoIds, product.getId()));
		productBuilder.images(productImageService.createProductImages(imageBytes, product.getId()));
		
		return productBuilder.build();
	}

	@Override
	public byte[] getLogoByProductId(final int productId) {
		return productDao.getLogoByProductId(productId);
	}
    
    @Transactional
    @Override
    public boolean deleteProductById(final int productId) {
    	return productDao.deleteProductById(productId);
    }
    
	@Override
	public List<Product> getPlainProductsByKeyword(String keyword) {
	    final String[] keywords = keyword.trim().split(" ");
	    final String[] fields ={FIRST_SEARCH_FIELD, SECOND_SEARCH_FIELD};
	    
	    Map<String, String> keyWordsRegExp = new HashMap<String, String>();
	   
	    StringBuilder likeQueryBuilder = new StringBuilder();

	    for (int i = 0; i < fields.length; i++) {
	    		    	
	    	if (i != 0)
	    		likeQueryBuilder.append(" OR ");
	    	
	    	likeQueryBuilder.append("(");
	    	
		    for (int j = 0; j < keywords.length; j++) {
		        if (keywords[j].length() >= MIN_WORD_SIZE) {
		        	
		        	if (j != 0)
			        	likeQueryBuilder.append(" AND ");
		        	
		        	String candidateKeyWord = keywords[j].toLowerCase();
		        	String firstKeyWord = "first" + candidateKeyWord;
		        	String otherKeyWord = "other" + candidateKeyWord;
		        	String firstKeyWordRegExp = candidateKeyWord + "%";
		        	String otherKeyWordRegExp = "% " + candidateKeyWord + "%";

		        	likeQueryBuilder.append("(");
		        	likeQueryBuilder.append(fields[i]);
		        	likeQueryBuilder.append(" LIKE ");
		        	likeQueryBuilder.append(":").append(firstKeyWord);
		        	
		        	likeQueryBuilder.append(" OR ");
		        	
		        	likeQueryBuilder.append(fields[i]);
		        	likeQueryBuilder.append(" LIKE ");
		        	likeQueryBuilder.append(":").append(otherKeyWord);
		        	likeQueryBuilder.append(")");
		        	
		        	keyWordsRegExp.put(firstKeyWord, firstKeyWordRegExp);
		        	keyWordsRegExp.put(otherKeyWord, otherKeyWordRegExp);
		        }
		    }
	    	
		    likeQueryBuilder.append(")");
	    }
	   
	    return productDao.getPlainProductsByKeyword(likeQueryBuilder.toString(), keyWordsRegExp);
	}

	@Override
	public List<Product> getPlainProductsPaged(Optional<Category> category, ProductSortCriteria sortCriteria, int page, int pageSize) {
		if (category.isPresent())
			return productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, (page - 1) * pageSize, pageSize); 

		return productDao.getPlainProductsRange(sortCriteria, (page - 1) * pageSize, pageSize); 
	}

	@Override
	public int getMaxProductPageWithSize(Optional<Category> category, int pageSize) {
		int total = category.isPresent() ? productDao.getTotalProductsInCategory(category.get()) : productDao.getTotalProducts();
		
		return (int) Math.ceil((float) total / pageSize);
	}	
}
