package tp.paw.khet.persistence;

import java.util.Date;
import java.util.List;
import java.util.Set;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;

public interface ProductDao {

	/**
	 * Creates a {@link Product} persisting it into the database.
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
	 * @param creator
	 *            - Product creator
	 * @return Product - The newly created product
	 */
	public Product createProduct(String name, String description, String shortDescription, String website,
			Category category, Date uploadDate, byte[] logo, User creator);

	/**
	 * Lists product created by {@link User} as a {@link Product} with the given
	 * userId.
	 * 
	 * @param userId
	 *            - ID of the creator
	 * @return List of products. Empty in case the user did not create any
	 *         product
	 */
	public List<Product> getPlainProductsByUserId(int userId);

	/**
	 * Retrieves the total amount of products registered.
	 * 
	 * @return The number of products.
	 */
	public int getTotalProducts();

	/**
	 * Retrieves the total amount of products registered for a given {@link Category}.
	 * 
	 * @param category
	 *            - Category the products belong to.
	 * @return The number of products in the given category.
	 */
	public int getTotalProductsInCategory(Category category);

	/**
	 * Lists a range of {@link Product} belonging to certain {@link Category}.
	 * Products are sorted by the given {@link ProductSortCriteria}.
	 * 
	 * @param category
	 *            - Category the products belong to
	 * @param sortCriteria
	 *            - Criteria by which products will be sorted
	 * @param offset
	 *            - Offset in the list of all products.
	 * @param length
	 *            - Length of the range to be retrieved
	 * @return {@link List} of the products in the given range. Could be empty
	 *         if offset is greater than the total number of products belonging
	 *         to the given {@link Category}. The amount of products retrieved
	 *         could be less than the length given.
	 */
	public List<Product> getPlainProductsRangeByCategory(Category category, ProductSortCriteria sortCriteria,
			int offset, int length);

	/**
	 * Lists a range of {@link Product}. Products are sorted by the given
	 * {@link ProductSortCriteria}.
	 * 
	 * @param sortCriteria
	 *            - Criteria by which products will be sorted
	 * @param offset
	 *            - Offset in the list of all products.
	 * @param length
	 *            - Length of the range to be retrieved
	 * @return {@link List} of the products in the given range. Could be empty
	 *         if offset is greater than the total number of products. The
	 *         amount of products retrieved could be less than the length given.
	 */
	public List<Product> getPlainProductsRange(ProductSortCriteria sortCriteria, int offset, int length);

	/**
	 * Retrieves a {@link Product} with every attribute set except for the
	 * familyComments.
	 * 
	 * @param productId
	 *            - ID of the product
	 * @return Product with the associated ID of null if it doesn't exist
	 */
	public Product getFullProductById(int productId);

	/**
	 * Retrieves a {@link Product} as a {@link Product}.
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
	 * Deletes a {@link Product} from the database.
	 * 
	 * @param productId
	 *            - ID of the product to delete
	 * @return true if a product was deleted
	 */
	public boolean deleteProductById(int productId);

	/**
	 * Retrieves a {@link List} of {@link Product} given a keyword String set.
	 * All keywords should match with a product's name or short description.
	 * 
	 * @param keywords
	 *            - The set of keywords which should be matched
	 * @param offset
	 *            - Offset in the list of all products
	 * @param length
	 *            - Length of the range to be retrieved
	 * @return The list of plain products which matched with the keywords.
	 */
	public List<Product> getPlainProductsByKeyword(Set<String> keywords, int offset, int length);

}
