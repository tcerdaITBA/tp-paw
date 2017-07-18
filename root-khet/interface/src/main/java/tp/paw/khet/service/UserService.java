package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface UserService {

	/**
	 * Creates an {@link User}.
	 * 
	 * @param userName
	 *            - User name of the new User
	 * @param email
	 *            - Email address of the new User
	 * @param password
	 *            - User's encrypted password
	 * @param profilePicture
	 *            - byte array with User's profile picture
	 * @return The created {@link User} or null if it's a duplicate
	 * @throws DuplicateEmailException
	 *             - if user with given exception already exists
	 */
	public User createUser(String userName, String email, String password, byte[] profilePicture)
			throws DuplicateEmailException;

	/**
	 * Retrieves an {@link User} given it's email.
	 * 
	 * @param email - Email address the {@link User} to retrieve is registered with
	 * @return The corresponding {@link User} or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Retrieves an {@link User} given it's ID
	 * 
	 * @param creatorId
	 *            - ID of the user
	 * @return The corresponding {@link User} or null if it doesn't exist
	 */
	public User getUserById(int userId);

	/**
	 * Retrieves a {@link List} of {@User} given a keyword. The keyword should
	 * match the user's name.
	 * 
	 * @param keyword - The keyword which should be matched
	 * @param page - index of the page to be retrieved
	 * @param pageSize - amount of users per page
	 * @return The list of plain products that match with the keyword.
	 */
	public List<User> getUsersByKeyword(String keyword, int page, int pageSize);

	/**
	 * Retrieves the amount of {@link User} pages available for a given page size 
	 * corresponding with the users which matched with the given keyword.
	 * @param keyword - The keyword which should be matched
	 * @param pageSize - Amount of users per page
	 * @return the maximum page number, which is the total number of pages for
	 *         the given size.
	 */
	public int getMaxUserPageByKeyword(String keyword, int pageSize);
	
	/**
	 * Retrieves an {@link User} profile picture.
	 * 
	 * @param userId
	 *            - ID of the user's profile picture to retrieve
	 * @return Byte array corresponding to the user's profile picture
	 */
	public byte[] getProfilePictureByUserId(int userId);

	/**
	 * Changes an {@link User} password
	 * 
	 * @param userId
	 *            - ID of the user
	 * @param password
	 *            - New user's password
	 * @return The user with the modified password or null if user doesn't exist
	 */
	public User changePassword(int userId, String password);

	/**
	 * Changes an {@link User} profile picture
	 * 
	 * @param userId
	 *            - ID of the user
	 * @param profilePicture
	 *            - Byte array with the new profile picture
	 * @return The user with the given ID
	 */
	public User changeProfilePicture(int userId, byte[] profilePicture);

	/**
	 * Retrieves the amount of {@link FavList} pages available for a given page size
	 * corresponding to the owner.
	 * @param userId - ID of the owner of the favLists
	 * @param pageSize - Amount of favLists per page
	 * @return the maximum page number, which is the total number of pages for
	 *         the given size.
	 */
	public int getMaxFavListsPageWithSize(int userId, int pageSize);

	/**
	 * Retrieves a list of {@link FavList} corresponding to the owner.
	 * @param userId - ID of the owner of the favLists
	 * @param page - index of the page to be retrieved
	 * @param pageSize - amount of favLists per page
	 * @return The list of favLists corresponding to the owner and page requested.
	 */
	public List<FavList> getFavListsByUserId(int userId, int page, int pageSize);

	/**
	 * Retrieves the amount of voted {@link Product} pages available for a given page size
	 * corresponding to the voter.
	 * @param userId - ID of the voter of the products
	 * @param pageSize - Amount of products per page
	 * @return the maximum page number, which is the total number of pages for
	 *         the given size.
	 */
	public int getMaxVotedProductsPageWithSize(int userId, int pageSize);

	/**
	 * Retrieves a list of voted {@link Product} corresponding to the voter.
	 * @param userId - ID of the voter of the products
	 * @param page - index of the page to be retrieved
	 * @param pageSize - amount of products per page
	 * @return The list of products corresponding to the voter and page requested.
	 */
	public List<Product> getVotedProductsByUserId(int userId, int page, int pageSize);

	/**
	 * Retrieves the amount of created {@link Product} pages available for a given page size
	 * corresponding to the creator.
	 * @param userId - ID of the creator of the products
	 * @param pageSize - Amount of products per page
	 * @return the maximum page number, which is the total number of pages for
	 *         the given size.
	 */
	public int getMaxCreatedProductsPageWithSize(int userId, int pageSize);

	/**
	 * Retrieves a list of created {@link Product} corresponding to the creator.
	 * @param userId - ID of the creator of the products
	 * @param page - index of the page to be retrieved
	 * @param pageSize - amount of products per page
	 * @return The list of products corresponding to the creator and page requested.
	 */
	public List<Product> getCreatedProductsByUserId(int id, int page, int pageSize);
}
