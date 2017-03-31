package tp.paw.khet.persistence;

import java.time.LocalDate;
import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductDao {
	public Product createProduct(String name, String description, String shortDescription, LocalDate uploadDate, byte[] logo, int creatorId);
	public List<Product> getProducts();
	public User getCreatorByProductId(int id);
}
