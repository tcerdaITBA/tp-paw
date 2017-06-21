package tp.paw.khet.controller.auth;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.User;

public interface SecurityUserService {
	/**
	 * Retrieves the logged in {@link User}. Method should only be called if a
	 * user is logged in.
	 * 
	 * @return The logged in user
	 */
	public User getLoggedInUser();

	/**
	 * Registers an {@link User} encrypting it's password
	 * 
	 * @param name
	 *            - User's name
	 * @param email
	 *            - User's email
	 * @param password
	 *            - User's raw password
	 * @param profilePicture
	 *            - User's profile picture
	 * @return The new created user
	 * @throws DuplicateEmailException
	 *             - if user with given email already exists
	 */
	public User registerUser(String name, String email, String password, byte[] profilePicture) throws DuplicateEmailException;

	/**
	 * Changes an {@link User} current password
	 * 
	 * @param userId
	 *            - ID of the user
	 * @param newPassword
	 *            - New user's password
	 * @return The user with the new password
	 */
	public User changePassword(int userId, String newPassword);
}