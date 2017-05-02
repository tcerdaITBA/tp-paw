package tp.paw.khet.webapp.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.exception.DuplicateEmailException;
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
		return new ModelAndView("createUser");
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
	public ModelAndView register(@ModelAttribute("createUserForm") @Valid FormUser createUserForm, 
			final BindingResult errors, RedirectAttributes attr) throws IOException {

		if (errors.hasErrors()) {
			return errorState(createUserForm, errors, attr);
		}
		
		try {
			securityUserService.registerUser(createUserForm.getName(), createUserForm.getEmail(), createUserForm.getPassword(), createUserForm.getProfilePicture().getBytes());
		} catch (DuplicateEmailException e) {
			LOGGER.warn("Duplicate email exception: {}", e.getMessage());
			return errorState(createUserForm, errors, attr);
		}
		
		return new ModelAndView("redirect:/");
	}
	
	private ModelAndView errorState(FormUser createUserForm, final BindingResult errors, RedirectAttributes attr) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.createUserForm", errors);
		attr.addFlashAttribute("createUserForm", createUserForm);
		return new ModelAndView("redirect:/register");		
	}
}