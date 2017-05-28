package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.model.Product.ProductBuilder;
import tp.paw.khet.model.interfaces.PlainProduct;

public interface ProductDao {
	
	/**
	 * Creates a {@link Product.ProductBuilder} inserting the {@link Product} data into the database.
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
	public ProductBuilder createProduct(String name, String description, String shortDescription, String website, String category,
			LocalDateTime uploadDate, byte[] logo, int creatorId);
	
	/**
	 * Lists every existing {@link Product} as a {@link PlainProduct}.
	 * @return {@link List} of the existing products
	 */
	public List<PlainProduct> getPlainProducts();
	
	/**
	 * Lists product created by {@link User} as a {@link PlainProduct} with the given userId.
	 * @param userId - ID of the creator
	 * @return List of products. Empty in case the user did not create any product
	 */
	public List<PlainProduct> getPlainProductsByUserId(int userId);
	
	 /**
     * Lists a range of {@link PlainProduct}.
     * Products are ordered by uploadDate.
     * @param offset - Offset in the list of all products.
     * @param length - Length of the range to be retrieved
     * @return {@link List} of the products in the given range. 
     *         Could be empty if offset is greater than the total number of products available.
     *         The amount of products retrieved could be smaller than the length given.
     */
    public List<PlainProduct> getPlainProductsRange(int offset, int length);
    
    /**
    * Lists a range of {@link PlainProduct}.
    * Products are ordered alphabetically by product name.
    * @param offset - Offset in the list of all products.
    * @param length - Length of the range to be retrieved
    * @return {@link List} of the products in the given range. 
    *         Could be empty if offset is greater than the total number of products available.
    *         The amount of products retrieved could be smaller than the length given.
    */
   public List<PlainProduct> getPlainProductsRangeAlphabetically(int offset, int length);
	
	/**
	 * Lists {@link PlainProduct} belonging to certain {@link Category}.
	 * @param category - Category the products belong to
	 * @return {@link List} of the products belonging to the category. 
	 * 		   Could be empty if there are no products registered in given category.
	 */
	public List<PlainProduct> getPlainProductsByCategory(String category);
	
	 /**
     * Lists a range of {@link PlainProduct} belonging to certain {@link Category}.
     * Products are ordered by uploadDate.
     * @param offset - Offset in the list of all products.
     * @param category - Category the products belong to
     * @param length - Length of the range to be retrieved
     * @return {@link List} of the products in the given range. 
     *         Could be empty if offset is greater than the total number of products belonging
     *         to the given {@link Category}.
     *         The amount of products retrieved could be smaller than the length given.
     */
    public List<PlainProduct> getPlainProductsRangeByCategory(String category, int offset, int length);
    
    /**
    * Lists a range of {@link PlainProduct} belonging to certain {@link Category}.
    * Products are ordered alphabetically by product name.
    * @param offset - Offset in the list of all products.
    * @param category - Category the products belong to
    * @param length - Length of the range to be retrieved
    * @return {@link List} of the products in the given range. 
    *         Could be empty if offset is greater than the total number of products belonging
    *         to the given {@link Category}.
    *         The amount of products retrieved could be smaller than the length given.
    */
    public List<PlainProduct> getPlainProductsRangeAlphabeticallyByCategory(String category, int offset, int length);

    /**
     * Retrieves the total amount of products registered.
     * @return The number of products.
     */
    public int getTotalProducts();
    
    /**
     * Retrieves the total amount of products registered for a given {@link Category}.
     * @param category - Category the products belong to.
     * @return The number of products in the given category.
     */
    public int getTotalProductsInCategory(Category category);
    
	/**
	 * Retrieves a {@link Product.ProductBuilder} with every attribute set except for 
	 * the familyComments and videos.
	 * @param productId - ID of the product
	 * @return Product with the associated ID of null if it doesn't exist
	 */
	public Product.ProductBuilder getFullProductById(int productId);
	
	/**
	 * Retrieves a {@link Product} as a {@link PlainProduct}.
	 * @param productId - ID of the product
	 * @return Plain Product with the associated ID or null if it doesn't exist
	 */
	public PlainProduct getPlainProductById(int productId);
	
	/**
	 * Retrieves the logo of a {@link Product}.
	 * @param productId - ID of the product the logo belongs to.
	 * @return byte array containing the image bytes
	 */
	public byte[] getLogoByProductId(int productId);

	/**
	 * Deletes a {@link Product} from the database.
	 * @param productId - ID of the product to delete
	 * @return true if a product was deleted
	 */
	public boolean deleteProductById(int productId);

	/**
	 * Retrieves a {@link List} of {@link PlainProduct} given a keyword.
	 * The keyword should match the product's name or short description.
	 * @param keyword - The keyword which should be matched
	 * @param maxLength - The maximum length of the returned list
	 * @return The list of plain products that match with the keyword.
	 */
	public List<PlainProduct> getPlainProductsByKeyword(String keyword, int maxLength);
}
