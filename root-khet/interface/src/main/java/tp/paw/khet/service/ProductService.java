package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface ProductService {
	
	/**
	 * Creates a {@link Product}.
	 * @param name - Name of the product
	 * @param description - Description of the product
	 * @param shortDescription - Brief comment or description of the product
	 * @param website - Official product website URL
	 * @param category - Category the product belongs to
	 * @param logo - Logo or image that identifies the product
	 * @param creatorId - Product creator ID
	 * @return Product
	 */
	public Product createProduct(String name, String description, String shortDescription, String website, Category category, byte[] logo, int creatorId);
	
	/**
	 * Returns a {@link User} given a {@link Product} ID.
	 * @param id - ID of a product
	 * @return user - creator
	 */
	public User getCreatorByProductId(int productId);
	
	
	/**
	 * Returns a {@link Product} given its ID
	 * @param id - Product's ID
	 * @return product
	 */
	public Product getProduct(int productId);
	
	/**
	 * Lists every existing {@link Product}
	 * @return List of the existing products
	 */
	public List<Product> getProducts();
	
	/**
	 * Lists products belonging to certain category
	 * @param category - category the products belong to
	 * @return List of the products belonging to the category.<p>
	 * 		   If there are no products in given category, the returned List is empty.
	 */
	public List<Product> getProductsByCategory(Category category);
	
	/**
	 * Returns a logo or image given a {@link Product} ID
	 * @param productId - ID of the product
	 * @return byte array containing the image bytes
	 */
	public byte[] getLogoByProductId(int productId);
}
