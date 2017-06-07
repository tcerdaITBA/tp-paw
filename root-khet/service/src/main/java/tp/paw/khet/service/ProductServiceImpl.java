package tp.paw.khet.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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

    //TODO: Arreglar para que devuelva todos y haga la interseccion entre estos no una cantidad maxLength
	@Override
	public List<Product> getPlainProductsByKeyword(String keyword, int maxLength) {
	    final String[] splitted = keyword.trim().split(" ");
	    final List<Product> result = productDao.getPlainProductsByKeyword(splitted[0], maxLength);

	    for (int i = 1; i < splitted.length; i++) {
	        if (splitted[i].length() >= 3)
	            result.retainAll(productDao.getPlainProductsByKeyword(splitted[i], maxLength));
	    }
	   
		return result;
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
