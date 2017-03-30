package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.persistence.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	public User getProductCreator(Product product) {
		return productDao.getCreatorByProductId(product.getId());
	}

	public List<Product> getProducts() {
		return productDao.getProducts();
	}

	// para testing con mocks
	void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
}
