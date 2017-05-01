package tp.paw.khet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tp.paw.khet.User;
import tp.paw.khet.persistence.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(String userName, String email, String rawPassword) {
		return userDao.createUser(userName, email.toLowerCase(), passwordEncoder.encode(rawPassword));
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public User getUserById(int userId) {
		return userDao.getUserById(userId);
	}
}