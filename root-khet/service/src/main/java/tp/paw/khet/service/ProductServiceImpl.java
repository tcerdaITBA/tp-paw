package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.persistence.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public User getCreatorByProductId(int productId) {
		return productDao.getCreatorByProductId(productId);
	}

	@Override
	public Product getProductById(int productId) {
		return productDao.getProductById(productId);
	}

	@Override
	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	@Override
	public List<Product> getProductsByCategory(Category category) {
		return productDao.getProductsByCategory(category.name());
	}
	
	@Override
	public Product createProduct(String name, String description, String shortDescription, String website, Category category, byte[] logo, int creatorId) {
		return productDao.createProduct(name, description, shortDescription, website, category.name(), LocalDateTime.now(),  logo, creatorId);
	}

	@Override
	public byte[] getLogoByProductId(int productId) {
		return productDao.getLogoByProductId(productId);
	}
}
