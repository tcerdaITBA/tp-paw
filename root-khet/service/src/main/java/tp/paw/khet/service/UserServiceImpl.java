package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.User;
import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.persistence.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User createUser(String userName, String email, String password, byte[] profilePicture) throws DuplicateEmailException {
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
	public List<User> getUsersByKeyword(String keyword) {
		return userDao.getUsersByKeyword(keyword);
	}
	
	@Override
	public byte[] getProfilePictureByUserId(int userId) {
		return userDao.getProfilePictureByUserId(userId);
	}
}