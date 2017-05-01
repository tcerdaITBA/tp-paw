package tp.paw.khet.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.UserService;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User getLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		return userService.getUserByEmail(email);
	}

	@Override
	public User registerUser(String name, String email, String password, byte[] profilePicture) {
		return userService.createUser(name, email, passwordEncoder.encode(password), profilePicture);
	}

}
