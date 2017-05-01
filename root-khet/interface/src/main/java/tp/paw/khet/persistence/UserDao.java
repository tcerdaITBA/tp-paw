package tp.paw.khet.persistence;

import tp.paw.khet.User;

public interface UserDao {
	
	/**
	 * Creates an {@link User} inserting it into the database.
	 * @param userName - User name of the new User
	 * @param email - Email address of the new User
	 * @param password - User's crypted password.
	 * @return The created {@link User} or null if it's a duplicate
	 */
	public User createUser(String userName, String email, String password);

	/**
	 * Retrieves an {@link User} given it's email.
	 * @param email - Email address the {@link User} to retrieve is registered with
	 * @return The corresponding {@link User} or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Retrieves an {@link User} given it's id.
	 * @param userId - ID of the {@link User} to retrieve
	 * @return The corresponding {@link User} or null if it doesn't exist
	 */
	public User getUserById(int userId);

}
