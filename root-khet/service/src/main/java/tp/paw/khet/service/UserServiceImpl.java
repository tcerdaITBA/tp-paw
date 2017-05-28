package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.User;
import tp.paw.khet.persistence.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Transactional
	@Override
	public User createUser(final String userName, final String email, final String password, final byte[] profilePicture) throws DuplicateEmailException {
		return userDao.createUser(userName, email.toLowerCase(), password, profilePicture);
	}

	@Override
	public User getUserByEmail(final String email) {
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public User getUserById(final int userId) {
		return userDao.getUserById(userId);
	}
	
	@Override
	public List<User> getUsersByKeyword(String keyword, int maxLength) {
		return userDao.getUsersByKeyword(keyword, maxLength);
	}
	
	@Override
	public byte[] getProfilePictureByUserId(final int userId) {
		return userDao.getProfilePictureByUserId(userId);
	}

	@Transactional
	@Override
	public User changePassword(final int userId, final String password) {
		return userDao.changePassword(userId, password);
	}

	@Transactional
	@Override
	public User changeProfilePicture(final int userId, final byte[] profilePicture) {
		return userDao.changeProfilePicture(userId, profilePicture);
	}
}