package tp.paw.khet.webapp.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;

@ControllerAdvice
public class UserControllerAdvice {

	@Autowired
	private SecurityUserService securityUserService;
	
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
	
}
