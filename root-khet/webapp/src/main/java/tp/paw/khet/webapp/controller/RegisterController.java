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
import tp.paw.khet.webapp.form.FormUser;
import tp.paw.khet.webapp.validators.PasswordConfirmValidator;

@Controller
public class RegisterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@Autowired
	private PasswordConfirmValidator PasswordConfirmValidator;
	
	@ModelAttribute("createUserForm")
	public FormUser createUserForm() {
		return new FormUser();
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.GET})
	public ModelAndView register() {
		return new ModelAndView("createUser");
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
	public ModelAndView register(@ModelAttribute("createUserForm") @Valid FormUser createUserForm, 
			final BindingResult errors, RedirectAttributes attr) throws IOException {

		PasswordConfirmValidator.validate(createUserForm, errors);
		
		if (errors.hasErrors())
			return errorState(createUserForm, errors, attr);
		User user;
		try {
			user = securityUserService.registerUser(createUserForm.getName(), createUserForm.getEmail(), createUserForm.getPassword(), createUserForm.getProfilePicture().getBytes());
		} catch (DuplicateEmailException e) {
			LOGGER.warn("Duplicate email exception: {}", e.getMessage());
			errors.rejectValue("email", "DuplicateEmail");
			return errorState(createUserForm, errors, attr);
		}
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(auth);
		return new ModelAndView("redirect:/");
	}
	
	private ModelAndView errorState(FormUser createUserForm, final BindingResult errors, RedirectAttributes attr) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.createUserForm", errors);
		attr.addFlashAttribute("createUserForm", createUserForm);
		return new ModelAndView("redirect:/register");		
	}
}