package tp.paw.khet.service;

import tp.paw.khet.User;

public interface UserService {
	
	/**
	 * Creates an {@link User}.
	 * @param userName - User name of the new User
	 * @param email - Email address of the new User
	 * @return The created {@link User} or null if it's a duplicate
	 */
	public User createUser(String userName, String email);
	
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
	 * Attempts to create an {@link User} given it's username and email. <p>
	 * If it alreadys exists, this method returns the already registered User.
	 * Note that in this case the userName parameter may not be the same as the returned User. <p>
	 * This method guarantees to never return null.
	 * @param userName - Name of the new User
	 * @param email - Email address of the new User
	 * @return User with the given userName and email if created or the User that already existed.
	 */
	public User createUserOrRetrieveIfExists(String userName, String email);

}
