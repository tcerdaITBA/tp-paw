package tp.paw.khet.service;

import tp.paw.khet.User;

public interface UserService {
	
	/**
	 * Creates an {@link User} given it's username and email
	 * @param userName
	 * @param email
	 * @return Created user or null if it's a duplicate
	 */
	public User createUser(String userName, String email);
	
	/**
	 * Returns an {@link User} given it's email
	 * @param email - email User is registered with
	 * @return The corresponding User or null if it doesn't exist
	 */
	public User getUserByEmail(String email);

	/**
	 * Attempts to create an {@link User} given it's username and email. <p>
	 * If it alreadys exists, this method returns the already registered User.
	 * Notice that in this case the userName parameter may not be the same as the returned User. <p>
	 * This method guarantees to never return null.
	 * @param userName - name of the new User
	 * @param email - email of the new User
	 * @return User with the given userName and email if created or the User that already existed.
	 */
	public User createUserOrRetrieveIfExists(String userName, String email);
}
