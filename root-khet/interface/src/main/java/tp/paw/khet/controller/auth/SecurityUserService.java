package tp.paw.khet.controller.auth;

import tp.paw.khet.User;

public interface SecurityUserService {
	/**
	 * Retrieves the logged in {@link User}. 
	 * Method should only be called if a user is logged in.
	 * @return The logged in user
	 */
	public User getLoggedInUser();
	
	/**
	 * Registers a {@link User} encrypting it's password
	 * @param name - User's name
	 * @param email - User's email
	 * @param password - User's raw password
	 * @param profilePicture - User's profile picture
	 * @return The new created user
	 */
	public User registerUser(String name, String email, String password, byte[] profilePicture);
}