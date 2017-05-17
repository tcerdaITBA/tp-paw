package tp.paw.khet.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.webapp.form.FormChangePassword;
import tp.paw.khet.webapp.form.FormPassword;
import tp.paw.khet.webapp.validators.PasswordChangeValidator;

@Controller
@SessionAttributes("changePasswordForm")
public class ProfileCustomizeController {

	@Autowired
	private SecurityUserService securityUserService;

	@Autowired
	private PasswordChangeValidator passwordChangeValidator;
	    
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
		
	@RequestMapping(value = "/profile/customize/password", method = {RequestMethod.POST})
	public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") final FormChangePassword changePasswordForm,
			final BindingResult errors, @ModelAttribute("loggedUser") final User loggedUser,
			RedirectAttributes attr) {
		
		passwordChangeValidator.validate(changePasswordForm, errors);
		
		if (errors.hasErrors())
			return errorState(changePasswordForm, errors, attr, loggedUser);
		
		FormPassword passwordForm = changePasswordForm.getPasswordForm();
		securityUserService.changePassword(loggedUser.getUserId(), passwordForm.getPassword());
		
		return new ModelAndView("redirect:/profile/" + loggedUser.getUserId());	
	}

	private ModelAndView errorState(FormChangePassword changePasswordForm, BindingResult errors, RedirectAttributes attr, User loggedUser) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", errors);
		attr.addFlashAttribute("changePasswordForm", changePasswordForm);
		return new ModelAndView("redirect:/profile/" + loggedUser.getUserId() + "?passwordError=1");	
	}
}

