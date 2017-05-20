package tp.paw.khet.webapp.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.webapp.form.FormPassword;
import tp.paw.khet.webapp.form.FormUser;

@Controller
public class RegisterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@ModelAttribute("createUserForm")
	public FormUser createUserForm() {
		return new FormUser();
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.GET})
	public ModelAndView register() {
		LOGGER.debug("Accessed register");
	    return new ModelAndView("createUser");
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
	public ModelAndView register(@ModelAttribute("createUserForm") @Valid final FormUser createUserForm, 
			final BindingResult errors, final RedirectAttributes attr) throws IOException {

		LOGGER.debug("Accessed register POST");
		
		FormPassword passwordForm = createUserForm.getPasswordForm();
		
		if (errors.hasErrors()) {
			LOGGER.warn("Failed to register user: form has error: {}", errors.getAllErrors());
			return errorState(createUserForm, errors, attr);
		}
		
		User user;
		
		try {
			user = securityUserService.registerUser(createUserForm.getName(), createUserForm.getEmail(), passwordForm.getPassword(), createUserForm.getProfilePicture().getBytes());
		} catch (DuplicateEmailException e) {
			LOGGER.warn("Failed to register user: duplicate email {}", e.getMessage());
			errors.rejectValue("email", "DuplicateEmail");
			return errorState(createUserForm, errors, attr);
		}
		
		LOGGER.info("New user with id {} registered", user.getUserId());
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return new ModelAndView("redirect:/");
	}
	
	private ModelAndView errorState(final FormUser createUserForm, final BindingResult errors, final RedirectAttributes attr) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.createUserForm", errors);
		attr.addFlashAttribute("createUserForm", createUserForm);
		return new ModelAndView("redirect:/register");		
	}
}