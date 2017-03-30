package tp.paw.khet.persistence;

import tp.paw.khet.User;

public interface UserDao {
	public User createUser(String userName, String email);
}
