package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.User;
import tp.paw.khet.exception.DuplicateEmailException;

public interface UserDao {
	
	/**
	 * Creates an {@link User} inserting it into the database.
	 * @param userName - User name of the new User
	 * @param email - Email address of the new User
	 * @param password - User's crypted password.
	 * @param profilePicture - Byte array with user's profile picture
	 * @return The created user or null if it's a duplicate
	 * @throws DuplicateEmailException - if user with given email aready exists
	 */
	public User createUser(String userName, String email, String password, byte[] profilePicture) throws DuplicateEmailException ;

	/**
	 * Retrieves an {@link User} given it's email.
	 * @param email - Email address the user to retrieve is registered with
	 * @return The corresponding user or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Retrieves an {@link User} given it's id.
	 * @param userId - ID of the user to retrieve
	 * @return The corresponding user or null if it doesn't exist
	 */
	public User getUserById(int userId);
	
	/**
	 * Retrieves a {@link List} of {@User} given a keyword.
	 * The keyword should match the user's name.
	 * @param keyword - The keyword which should be matched
	 * @param maxLength - The maximum length of the returned list
	 * @return The list of plain products that match with the keyword.
	 */
	public List<User> getUsersByKeyword(String keyword, int maxLength);

	/**
	 * Retrieves an {@link User} profile picture.
	 * @param userId - ID of the user's profile picture to retrieve
	 * @return Byte array corresponding to the user's profile picture
	 */
	public byte[] getProfilePictureByUserId(int userId);
}
