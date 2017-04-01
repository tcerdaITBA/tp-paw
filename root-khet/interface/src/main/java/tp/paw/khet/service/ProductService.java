package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductService {
	public Product createProduct(String name, String description, String shortDescription, byte[] logo, int creatorId);
	public User getProductCreator(Product product);
	public List<Product> getProducts();
	public byte[] getLogoByProductId(int productId);
}
