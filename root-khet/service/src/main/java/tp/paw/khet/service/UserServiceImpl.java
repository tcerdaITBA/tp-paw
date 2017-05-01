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
	public User createUser(String userName, String email, String password, byte[] profilePicture) {
		return userDao.createUser(userName, email.toLowerCase(), password, profilePicture);
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
	public byte[] getProfilePictureByUserId(int userId) {
		return userDao.getProfilePictureByUserId(userId);
	}
}