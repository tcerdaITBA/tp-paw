package tp.paw.khet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public Product createProduct(final String name, final String description, final String shortDescription,
			final String website, final Category category, final byte[] logo, final int creatorId,
			final List<byte[]> imageBytes, final List<String> videoIds) {

		final Product product = productDao.createProduct(name, description, shortDescription, website, category, new Date(), 
				logo, userService.getUserById(creatorId));

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
	public List<Product> getPlainProductsByKeyword(final String keyword, final int page, final int pageSize) {
		final Set<String> validKeywords = buildValidKeywords(keyword);
		
		if (validKeywords.isEmpty())
			return new ArrayList<Product>();

		return productDao.getPlainProductsByKeyword(validKeywords, (page - 1) * pageSize, pageSize);
	}

	@Override
	public int getMaxProductsPageByKeyword(final String keyword, final int pageSize) {
		final Set<String> validKeywords = buildValidKeywords(keyword);
		final int total = productDao.getTotalProductsByKeyword(validKeywords);
		
		return (int) Math.ceil((float) total / pageSize);
	}
	
	private Set<String> buildValidKeywords(final String keyword) {
		final String[] keywords = keyword.trim().split(" ");
		final Set<String> validKeywords = new HashSet<>();

		for (final String word : keywords)
			if (word.length() >= MIN_WORD_SIZE)
				validKeywords.add(word);
		
		return validKeywords;
	}
	
	@Override
	public List<Product> getPlainProductsPaged(final Optional<Category> category, final ProductSortCriteria sortCriteria, 
			final int page, final int pageSize) {
		
		if (category.isPresent())
			return productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, (page - 1) * pageSize, pageSize);

		return productDao.getPlainProductsRange(sortCriteria, (page - 1) * pageSize, pageSize);
	}

	@Override
	public int getMaxProductPageWithSize(final Optional<Category> category, final int pageSize) {
		int total = category.isPresent() ? productDao.getTotalProductsInCategory(category.get()) : productDao.getTotalProducts();

		return (int) Math.ceil((float) total / pageSize);
	}
}
