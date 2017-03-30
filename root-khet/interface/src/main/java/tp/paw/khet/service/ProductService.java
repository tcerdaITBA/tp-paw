package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductService {
	public User getProductCreator(Product product);
	public List<Product> getProducts();
}
