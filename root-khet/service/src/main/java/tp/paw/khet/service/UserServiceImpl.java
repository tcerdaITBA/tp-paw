package tp.paw.khet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.User;
import tp.paw.khet.persistence.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User createUser(String userName, String email) {
		return userDao.createUser(userName, email.toLowerCase());
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public User createUserOrRetrieveIfExists(String userName, String email) {
		User user = createUser(userName, email);
		
		if (user == null)
			user = getUserByEmail(email);
		
		if (user == null)
			throw new IllegalStateException("User with email " + email + " must exist");
		
		return user;
	}
}