package tp.paw.khet.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.Product.ProductBuilder;
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
	public List<Product> getPlainProducts() {
		return productDao.getPlainProducts();
	}

	@Override
	public List<Product> getPlainProductsByUserId(final int userId) {
		return productDao.getPlainProductsByUserId(userId);
	}
	
	@Override
	public List<Product> getPlainProductsByCategory(final Category category) {
		return productDao.getPlainProductsByCategory(category);
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

    @Override
    public List<Product> getPlainProductsPaged(final int page, final int pageSize) {
        return productDao.getPlainProductsRange((page - 1) * pageSize, pageSize);
    }

    @Override
    public List<Product> getPlainProductsByCategoryPaged(final Category category, final int page,
    		final int pageSize) {
        return productDao.getPlainProductsRangeByCategory(category, (page - 1) * pageSize, pageSize);
    }

    @Override
    public int getMaxProductPageWithSize(final int pageSize) {
        int total = productDao.getTotalProducts();
        return (int) Math.ceil((float) total / pageSize);
    }

    @Override
    public int getMaxProductPageInCategoryWithSize(final Category category, final int pageSize) {
        int total = productDao.getTotalProductsInCategory(category);
        return (int) Math.ceil((float) total / pageSize);
    }
    
    @Transactional
    @Override
    public boolean deleteProductById(final int productId) {
    	return productDao.deleteProductById(productId);
    }

    @Override
    public List<Product> getPlainProductsAlphabeticallyPaged(final int page, final int pageSize) {
        return productDao.getPlainProductsRangeAlphabetically((page - 1) * pageSize, pageSize);
    }

    @Override
    public List<Product> getPlainProductsAlphabeticallyByCategoryPaged(final Category category, final int page,
    		final int pageSize) {
        return productDao.getPlainProductsRangeAlphabeticallyByCategory(category, (page - 1) * pageSize, pageSize);
    }

	@Override
	public List<Product> getPlainProductsByKeyword(String keyword, int maxLength) {
		return productDao.getPlainProductsByKeyword(keyword, maxLength);
	}
}
