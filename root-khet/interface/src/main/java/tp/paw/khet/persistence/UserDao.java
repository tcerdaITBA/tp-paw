package tp.paw.khet.persistence;

import java.util.List;
import java.util.Set;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface UserDao {

	/**
	 * Creates an {@link User} inserting it into the database.
	 * 
	 * @param userName
	 *            - User name of the new User
	 * @param email
	 *            - Email address of the new User
	 * @param password
	 *            - User's crypted password.
	 * @param profilePicture
	 *            - Byte array with user's profile picture
	 * @return The created user or null if it's a duplicate
	 * @throws DuplicateEmailException
	 *             - if user with given email aready exists
	 */
	public User createUser(String userName, String email, String password, byte[] profilePicture)
			throws DuplicateEmailException;

	/**
	 * Retrieves an {@link User} given it's email.
	 * 
	 * @param email
	 *            - Email address the user to retrieve is registered with
	 * @return The corresponding user or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Retrieves an {@link User} given it's id.
	 * 
	 * @param userId
	 *            - ID of the user to retrieve
	 * @return The corresponding user or null if it doesn't exist
	 */
	public User getUserById(int userId);

	/**
	 * Retrieves a {@link List} of {@User} given a keyword. The keyword should
	 * match the user's name.
	 * 
	 * @param keyword - The keywords which should be matched
	 * @param offset - Offset in the result list
	 * @param length - Length of the result list
	 * @return The list of plain products that match with the keyword.
	 */
	public List<User> getUsersByKeyword(Set<String> keywords, int offset, int length);

	/**
	 * Retrieves the total amount of users matching with the given set of keywords
	 * @param keyword - The keywords which should be matched
	 * @return The total amount of users who matched
	 */
	public int getTotalUsersByKeyword(Set<String> keywords);

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
	 * Lists a range of {@link FavList} owned by the corresponding user.
	 * @param userId - ID of the FavLists owner
	 * @param offset - Offset in the result list
	 * @param length - Length of the result list
	 * @return The {@link List} of FavLists owned by the corresponding user.
	 */
	public List<FavList> getFavListsRange(int userId, int offset, int length);

	/**
	 * Lists a range of {@link Product} voted by the corresponding user.
	 * @param userId - ID of the voter
	 * @param offset - Offset in the result list
	 * @param length - Length of the result list
	 * @return The {@link List} of products voted by the corresponding user.
	 */
	public List<Product> getVotedProductsRange(int userId, int offset, int length);

	/**
	 * Retrieves the total amount of {@link Product} created by the corresponding user.
	 * @param userId - ID of the creator
	 * @return The total amount of created products
	 */	
	public int getTotalCreatedProductsByUserId(int userId);

	/**
	 * Lists a range of {@link Product} created by the corresponding user.
	 * @param userId - ID of the creator
	 * @param offset - Offset in the result list
	 * @param length - Length of the result list
	 * @return The {@link List} of products created by the corresponding user.
	 */
	public List<Product> getCreatedProductsRange(int userId, int offset, int length);
}
