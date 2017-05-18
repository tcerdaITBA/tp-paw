package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.User;
import tp.paw.khet.exception.DuplicateEmailException;

public interface UserService {
	
	/**
	 * Creates an {@link User}.
	 * @param userName - User name of the new User
	 * @param email - Email address of the new User
	 * @param password - User's encrypted password
	 * @param profilePicture - byte array with User's profile picture
	 * @return The created {@link User} or null if it's a duplicate
	 * @throws DuplicateEmailException - if user with given exception already exists
	 */
	public User createUser(String userName, String email, String password, byte[] profilePicture) throws DuplicateEmailException;
	
	/**
	 * Retrieves an {@link User} given it's email.
	 * @param email - Email address the {@link User} to retrieve is registered with
	 * @return The corresponding {@link User} or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Retrieves an {@link User} given it's ID
	 * @param creatorId - ID of the user
	 * @return User object corresponding to the given id
	 */
	public User getUserById(int userId);

	/**
	 * Retrieves a {@link List} of {@User} given a keyword.
	 * The keyword should match the user's name.
	 * @param keyword - The keyword which should be matched
	 * @return The list of plain products that match with the keyword.
	 */
	public List<User> getUsersByKeyword(String keyword);
	
	/**
	 * Retrieves an {@link User} profile picture.
	 * @param userId - ID of the user's profile picture to retrieve
	 * @return Byte array corresponding to the user's profile picture
	 */
	byte[] getProfilePictureByUserId(int userId);
}
