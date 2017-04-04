package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductDao {
	public Product createProduct(String name, String description, String shortDescription, LocalDateTime uploadDate, byte[] logo, int creatorId);
	public List<Product> getProducts();
	public Product getProductByProductId(int id);
	public User getCreatorByProductId(int id);
	public byte[] getLogoByProductId(int productId);
}
