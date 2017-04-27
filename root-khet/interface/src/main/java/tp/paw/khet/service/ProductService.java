package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Category;
import tp.paw.khet.Product;

public interface ProductService {
	
	/**
	 * Creates a {@link Product}.
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
	public Product createProduct(String name, String description, String shortDescription, String website, Category category, byte[] logo, int creatorId);
	
	/**
	 * Lists every existing {@link Product} as a plain product: ID, name, short description and category.
	 * @return {@link List} of the existing products
	 */
	public List<Product> getPlainProducts();
	
	/**
	 * Lists plain products belonging to certain {@link Category}.
	 * @param category - Category the products belong to
	 * @return {@link List} of the products belonging to the category. 
	 * 		   Could be empty if there are no products registered in given category.
	 */
	public List<Product> getPlainProductsByCategory(Category category);
	
	/**
	 * Retrieves a {@link Product} with no null attributes.
	 * @param productId - ID of the product
	 * @return Product with the associated ID or null if it doesn't exist
	 */
	public Product getFullProductById(int productId);
	
	/**
	 * Retrieves a {@link Product} with only it's ID, name, short description and category.
	 * @param productId - ID of the product
	 * @return Plain Product with the associated ID or null if it doesn't exist
	 */
	public Product getPlainProductById(int productId);
		
	/**
	 * Retrieves the logo of a {@link Product}.
	 * @param productId - ID of the product the logo belongs to.
	 * @return byte array containing the image bytes
	 */
	public byte[] getLogoByProductId(int productId);
}
