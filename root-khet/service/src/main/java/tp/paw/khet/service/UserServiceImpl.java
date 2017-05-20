package tp.paw.khet.service;

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
	public byte[] getProfilePictureByUserId(final int userId) {
		return userDao.getProfilePictureByUserId(userId);
	}

	@Override
	public User changePassword(final int userId, final String password) {
		return userDao.changePassword(userId, password);
	}

	@Override
	public User changeProfilePicture(final int userId, final byte[] profilePicture) {
		return userDao.changeProfilePicture(userId, profilePicture);
	}
}