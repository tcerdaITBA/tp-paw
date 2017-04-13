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
	
	public User getCreatorByProductId(int productId) {
		return productDao.getCreatorByProductId(productId);
	}

	public Product getProduct(int productId) {
		return productDao.getProductByProductId(productId);
	}

	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	public Product createProduct(String name, String description, String shortDescription, Category category, byte[] logo, int creatorId) {
		return productDao.createProduct(name, description, shortDescription, category.name(), LocalDateTime.now(),  logo, creatorId);
	}

	public byte[] getLogoByProductId(int productId) {
		return productDao.getLogoByProductId(productId);
	}
}
