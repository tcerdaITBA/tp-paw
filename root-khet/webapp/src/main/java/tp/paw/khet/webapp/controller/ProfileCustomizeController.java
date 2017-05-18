package tp.paw.khet.webapp.controller;

import java.io.IOException;

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
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.form.FormChangePassword;
import tp.paw.khet.webapp.form.FormChangePicture;
import tp.paw.khet.webapp.form.FormPassword;
import tp.paw.khet.webapp.validators.PasswordChangeValidator;

@Controller
@SessionAttributes(value={"changePasswordForm","changeProfilePictureForm"})
public class ProfileCustomizeController {

	@Autowired
	private SecurityUserService securityUserService;

	@Autowired
	private PasswordChangeValidator passwordChangeValidator;
	
	@Autowired
	private UserService userService;
	    
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
		
	@RequestMapping(value = "/profile/customize/password", method = {RequestMethod.POST})
	public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") final FormChangePassword changePasswordForm,
			final BindingResult errors, @ModelAttribute("loggedUser") final User loggedUser,
			RedirectAttributes attr) {
		
		changePasswordForm.setCurrentPassword(loggedUser.getPassword());
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
	
	@RequestMapping(value="/profile/customize/profilePicture", method = {RequestMethod.POST})
	public ModelAndView changeProfilePicture(@Valid @ModelAttribute("changeProfilePictureForm") final FormChangePicture changeProfilePictureForm ,
			final BindingResult errors, @ModelAttribute("loggedUser") final User loggedUser,
			RedirectAttributes attr) {
		
		if (errors.hasErrors())
			return errorState(changeProfilePictureForm, errors, attr, loggedUser);
		
		try {
			userService.changeProfilePicture(loggedUser.getUserId(), changeProfilePictureForm.getProfilePicture().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/profile/" + loggedUser.getUserId());	
	}

	private ModelAndView errorState(FormChangePicture changeProfilePictureForm, BindingResult errors, RedirectAttributes attr, User loggedUser) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.changeProfilePictureForm", errors);
		attr.addFlashAttribute("changeProfilePictureForm", changeProfilePictureForm);
		return new ModelAndView("redirect:/profile/" + loggedUser.getUserId() + "?imgError=1");	
	}
}

