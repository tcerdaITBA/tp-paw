package tp.paw.khet.persistence;

import tp.paw.khet.User;

public interface UserDao {
	
	/**
	 * Creates an {@link User} given it's username and email
	 * @param userName
	 * @param email
	 * @return user
	 */
	public User createUser(String userName, String email);
}
