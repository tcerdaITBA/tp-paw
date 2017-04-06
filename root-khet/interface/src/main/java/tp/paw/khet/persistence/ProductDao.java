package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductDao {
	
	/**
	 * Creates a {@link Product}.
	 * @param name - Name of the product
	 * @param description - Description of the product
	 * @param shortDescription - Brief comment or description of the product
	 * @param uploadDate
	 * @param logo - Logo or image that identifies the product
	 * @param creatorId - Product creator ID
	 * @return Product
	 */
	public Product createProduct(String name, String description, String shortDescription, LocalDateTime uploadDate, byte[] logo, int creatorId);
	
	/**
	 * Lists every existing {@link Product}
	 * @return List of the existing products
	 */
	public List<Product> getProducts();
	
	/**
	 * Returns a {@link Product} given its creator's ID
	 * @param id - Creator's ID
	 * @return product
	 */
	public Product getProductByProductId(int id);
	
	/**
	 * Returns a {@link User} given a {@link Product} ID.
	 * @param id - ID of a product
	 * @return user - creator
	 */
	public User getCreatorByProductId(int id);
	
	/**
	 * Returns a logo or image given a {@link Product} ID
	 * @param productId - ID of the product
	 * @return byte array containing the image bytes
	 */
	public byte[] getLogoByProductId(int productId);
}
