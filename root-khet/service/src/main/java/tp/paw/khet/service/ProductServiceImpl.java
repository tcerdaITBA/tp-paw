package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.persistence.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	@Override
	public Product getFullProductById(int productId) {
		return productDao.getFullProductById(productId);
	}

	@Override
	public Product getPlainProductById(int productId) {
		return productDao.getPlainProductById(productId);
	}
	
	@Override
	public List<Product> getPlainProducts() {
		return productDao.getPlainProducts();
	}

	@Override
	public List<Product> getPlainProductsByCategory(Category category) {
		return productDao.getPlainProductsByCategory(category.name());
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
