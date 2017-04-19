package tp.paw.khet.persistence;

import tp.paw.khet.User;

public interface UserDao {
	
	/**
	 * Creates an {@link User} given it's username and email
	 * @param userName
	 * @param email
	 * @return Created User or null if it's a duplicate
	 */
	public User createUser(String userName, String email);

	/**
	 * Returns an {@link User} given it's email
	 * @param email - email User is registered with
	 * @return The corresponding User or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Returns an {@link User} given it's id
	 * @param userId - id of the User to retrieve
	 * @return The corresponding User
	 */
	public User getUserById(int userId);
}
