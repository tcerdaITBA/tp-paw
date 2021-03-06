package tp.paw.khet.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.User;
import tp.paw.khet.service.UserService;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User getLoggedInUser() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null)
			return null;

		final String email = auth.getName();
		return userService.getUserByEmail(email);
	}

	@Override
	public User registerUser(String name, String email, String password, byte[] profilePicture)
			throws DuplicateEmailException {
		return userService.createUser(name, email, passwordEncoder.encode(password), profilePicture);
	}

	@Override
	public User changePassword(int userId, String password) {
		return userService.changePassword(userId, passwordEncoder.encode(password));
	}
}
