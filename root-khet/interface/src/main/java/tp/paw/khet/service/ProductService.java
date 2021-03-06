package tp.paw.khet.service;

import java.util.List;
import java.util.Optional;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.OrderCriteria;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductSortCriteria;

public interface ProductService {

	/**
	 * Creates a {@link Product}.
	 * 
	 * @param name
	 *            - Name of the product
	 * @param description
	 *            - Description of the product
	 * @param shortDescription
	 *            - Brief comment or description of the product
	 * @param website
	 *            - Official product website URL
	 * @param category
	 *            - Category the product belongs to
	 * @param uploadDate
	 *            - Date the product was uploaded
	 * @param logo
	 *            - Logo or image that identifies the product
	 * @param creatorId
	 *            - ID of the product creator
	 * @param imageBytes
	 *            - List of bytes corresponding to the products images
	 * @param videoIds
	 *            - List of Youtube video's IDs.
	 * @return Product - The newly created product
	 */
	public Product createProduct(String name, String description, String shortDescription, String website,
			Category category, byte[] logo, int creatorId, List<byte[]> imageBytes, List<String> videoIds);

	/**
	 * Lists a range of {@link Product} sorted by the given
	 * {@link ProductSortCriteria}. The range given is [(page - 1) * pageSize,
	 * page * pageSize]. First page is number 1.
	 * <p>
	 * Optionally, the range may be filtered by a {@link Category}.
	 * 
	 * @param category - optional category the products belong to. See {@link Optional}.
	 * @param sortCriteria - product sort criteria to be used.
	 * @param order - whether should be ascendent or descendent sorted. See {@link OrderCriteria}.
	 * @param page - index of the page to be retrieved.
	 * @param pageSize - amount of products per page.
	 * @return Sorted {@link List} of the products belonging to the category if given. Otherwise, products belong to any category.<p>
	 *         Could be empty if there are no products registered.
	 */
	public List<Product> getPlainProductsPaged(Optional<Category> category, ProductSortCriteria sortCriteria, OrderCriteria order, int page,
			int pageSize);

	/**
	 * Retrieves the amount of {@link Product} pages available for a given page size.
	 * Optionally, a {@link Category} may be given to aquire the max page size only
	 * for products belonging in it.
	 * 
	 * @param category
	 *            - optional category the products belong to. See
	 *            {@link Optional}.
	 * @param pageSize
	 *            - amount of products per page
	 * @return the maximum page number, which is the total number of pages for
	 *         the given size.
	 */
	public int getMaxProductPageWithSize(Optional<Category> category, int pageSize);

	/**
	 * Retrieves a {@link Product} with no null attributes.
	 * 
	 * @param productId
	 *            - ID of the product
	 * @return Product with the associated ID or null if it doesn't exist
	 */
	public Product getFullProductById(int productId);

	/**
	 * Retrieves a {@link Product} as a {@link Product} given it's ID.
	 * 
	 * @param productId
	 *            - ID of the product
	 * @return Plain Product with the associated ID or null if it doesn't exist
	 */
	public Product getPlainProductById(int productId);

	/**
	 * Retrieves the logo of a {@link Product}.
	 * 
	 * @param productId
	 *            - ID of the product the logo belongs to.
	 * @return byte array containing the image bytes
	 */
	public byte[] getLogoByProductId(int productId);

	/**
	 * Deletes a {@link Product}.
	 * 
	 * @param productId
	 *            - ID of the product to delete
	 * @return true if a product was deleted
	 */
	public boolean deleteProductById(int productId);

	/**
	 * Retrieves a {@link List} of {@link Product} given a keyword. The keyword
	 * should match the product's name or short description.
	 * 
	 * @param keyword
	 *            - The keyword which should be matched
	 * @param page
	 *            - Index of the page to be retrieved
	 * @param pageSize
	 *            - Amount of products per page
	 * @return The list of plain products that match with the keyword.
	 */
	public List<Product> getPlainProductsByKeyword(String keyword, int page, int pageSize);

	/**
	 * Retrieves the amount of {@link Product} pages available for a given page size 
	 * corresponding with the products which matched with the given keyword.
	 * @param keyword - The keyword which should be matched
	 * @param pageSize - Amount of products per page
	 * @return the maximum page number, which is the total number of pages for
	 *         the given size.
	 */
	public int getMaxProductsPageByKeyword(String keyword, int pageSize);

	/**
	 * Retrieves the total amount of {@link Product} which matched with the given keyword.
	 * @param keyword - The keyword which should be matched
	 * @return the total amount of products which matched with the given keyword.
	 */
	public int getTotalProductsByKeyword(String keyword);

	/**
	 * Retrieves the total amount of {@link Product}.
	 * Optionally, a {@link Category} may be given to aquire the amount of
	 * products belonging in it.
	 * 
	 * @param category
	 *            - optional category the products belong to. See {@link Optional}.
	 * @return the total amount of products.
	 */
	public int getTotalProducts(Optional<Category> category);
}
