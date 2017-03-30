package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductDao {
	public List<Product> getProducts();
	public User getCreatorByProductId(int id);
}
