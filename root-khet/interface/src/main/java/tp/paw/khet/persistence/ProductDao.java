package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.Category;
import tp.paw.khet.User;

public interface ProductDao {
	
	/**
	 * Creates a {@link Product} inserting it into de database.
	 * @param name - Name of the product
	 * @param description - Description of the product
	 * @param shortDescription - Brief comment or description of the product
	 * @param website - Official product website URL
	 * @param category - Category the product belongs to
	 * @param uploadDate - Date the product was uploaded
	 * @param logo - Logo or image that identifies the product
	 * @param creatorId - ID of the product creator
	 * @return Product - The newly created product
	 */
	public Product createProduct(String name, String description, String shortDescription, String website, String category,
			LocalDateTime uploadDate, byte[] logo, int creatorId);
	
	/**
	 * Lists every existing {@link Product}.
	 * @return {@link List} of the existing products
	 */
	public List<Product> getProducts();
	
	/**
	 * Lists products belonging to certain {@link Category}.
	 * @param category - Category the products belong to
	 * @return {@link List} of the products belonging to the category. 
	 * 		   Could be empty if there are no products registered in given category.
	 */
	public List<Product> getProductsByCategory(String category);

	/**
	 * Retrieves a {@link Product}.
	 * @param productId - ID of the product
	 * @return Product with the associated ID of null if it doesn't exist
	 */
	public Product getProductById(int productId);
	
	/**
	 * Retrieves a {@link User} given a {@link Product} ID.
	 * @param productId - ID of the product the User created
	 * @return {@link User} representing the product's creator
	 */
	public User getCreatorByProductId(int productId);
	
	/**
	 * Retrieves the logo of a {@link Product}.
	 * @param productId - ID of the product the logo belongs to.
	 * @return byte array containing the image bytes
	 */
	public byte[] getLogoByProductId(int productId);
}
