package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.model.interfaces.PlainProduct;

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
	 * @param imageBytes - List of bytes corresponding to the products images
	 * @param videoIds - List of Youtube video's IDs.
	 * @return Product - The newly created product
	 */
	public Product createProduct(String name, String description, String shortDescription, String website, 
			Category category, byte[] logo, int creatorId, List<byte[]> imageBytes, List<String> videoIds);
	
	/**
	 * Lists every existing {@link Product} as a {@link PlainProduct}.
	 * @return {@link List} of the existing products
	 */
	public List<PlainProduct> getPlainProducts();
	
	/**
	 * Lists products created by {@link User} as a {@link PlainProduct} with the given userId.
	 * @param userId - ID of the creator
	 * @return List of products. Empty in case the user did not create any product
	 */
	public List<PlainProduct> getPlainProductsByUserId(int userId);
	
	/**
     * Lists a range of existing {@link Product} as a {@link PlainProduct}.
     * Products are ordered by uploadDate, the range given is [(page - 1) * pageSize, page * pageSize].
     * First page is number 1.
     * @param page - index of the page to be retrieved
     * @param pageSize - amount of products per page
     * @return {@link List} of the products in the given range. 
     */
	public List<PlainProduct> getPlainProductsPaged(int page, int pageSize);
	
	/**
     * Lists a range of existing {@link Product} as a {@link PlainProduct}.
     * Products are ordered alphabetically by their product name, the range given is [(page - 1) * pageSize, page * pageSize].
     * First page is number 1.
     * @param page - index of the page to be retrieved
     * @param pageSize - amount of products per page
     * @return {@link List} of the products in the given range. 
     */
    public List<PlainProduct> getPlainProductsAlphabeticallyPaged(int page, int pageSize);
    
	/**
	 * Lists {@link PlainProduct} belonging to certain {@link Category}.
	 * @param category - Category the products belong to
	 * @return {@link List} of the products belonging to the category. 
	 * 		   Could be empty if there are no products registered in given category.
	 */
	public List<PlainProduct> getPlainProductsByCategory(Category category);
	
	/**
	 * Lists a range of {@link PlainProduct} belonging to certain {@link Category}.
	 * Products are ordered by uploadDate, the range given is [(page - 1) * pageSize, page * pageSize] 
	 * First page is number 1.
	 * @param category Category the products belong to
	 * @param page - index of the page to be retrieved
	 * @param pageSize - amount of products per page
	 * @return {@link List} of the products belonging to the category. 
     *         Could be empty if there are no products registered in given category.
	 */
	public List<PlainProduct> getPlainProductsByCategoryPaged(Category category, int page, int pageSize);
	
	   /**
     * Lists a range of {@link PlainProduct} belonging to certain {@link Category}.
     * Products are ordered alphabetically by their product name, the range given is [(page - 1) * pageSize, page * pageSize] 
     * First page is number 1.
     * @param category Category the products belong to
     * @param page - index of the page to be retrieved
     * @param pageSize - amount of products per page
     * @return {@link List} of the products belonging to the category. 
     *         Could be empty if there are no products registered in given category.
     */
    public List<PlainProduct> getPlainProductsAlphabeticallyByCategoryPaged(Category category, int page, int pageSize);
 	
	/**
	 * Returns the amount of pages available for a given page size.
	 * @param pageSize - amount of products per page
	 * @return the maximum page number, which is the total number of pages for the given size.
	 */
	public int getMaxProductPageWithSize(int pageSize);
	
	   /**
     * Returns the amount of pages available for a given page size with products of a given category.
     * @param category - the category the products belong to.
     * @param pageSize - amount of products per page
     * @return the maximum page number, which is the total number of pages for the given size.
     */
    public int getMaxProductPageInCategoryWithSize(Category category, int pageSize);
	
	/**
	 * Retrieves a {@link Product} with no null attributes.
	 * @param productId - ID of the product
	 * @return Product with the associated ID or null if it doesn't exist
	 */
	public Product getFullProductById(int productId);
		
	/**
	 * Retrieves a {@link Product} as a {@link PlainProduct} given it's ID.
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
	 * Deletes a {@link Product}.
	 * @param productId - ID of the product to delete
	 * @return true if a product was deleted
	 */
	boolean deleteProductById(int productId);
	
	/**
	 * Retrieves a {@link List} of {@link PlainProduct} given a keyword.
	 * The keyword should match the product's name or short description.
	 * @param keyword - The keyword which should be matched
	 * @param maxLength - The maximum length of the returned list
	 * @return The list of plain products that match with the keyword.
	 */
	public List<PlainProduct> getPlainProductsByKeyword(String keyword, int maxLength);
}
