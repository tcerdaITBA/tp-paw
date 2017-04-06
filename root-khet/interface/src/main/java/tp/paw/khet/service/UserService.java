package tp.paw.khet.service;

import tp.paw.khet.User;

public interface UserService {
	
	/**
	 * Creates an {@link User} given it's username and email
	 * @param userName
	 * @param email
	 * @return user
	 */
	public User createUser(String userName, String email);
}
