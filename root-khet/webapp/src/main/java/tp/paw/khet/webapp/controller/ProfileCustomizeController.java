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

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCustomizeController.class);
	
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
		ModelAndView mav = new ModelAndView("redirect:/profile/" + loggedUser.getUserId());
		
		if (errors.hasErrors()) {
			setErrorState(changePasswordForm, errors, attr, loggedUser);
			return mav;
		}
		
		FormPassword passwordForm = changePasswordForm.getPasswordForm();
		securityUserService.changePassword(loggedUser.getUserId(), passwordForm.getPassword());
		
		return mav;	
	}

	private void setErrorState(FormChangePassword changePasswordForm, BindingResult errors, RedirectAttributes attr, User loggedUser) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", errors);
		attr.addFlashAttribute("changePasswordForm", changePasswordForm);
		attr.addFlashAttribute("passError", true);
	}
	
	@RequestMapping(value="/profile/customize/profilePicture", method = {RequestMethod.POST})
	public ModelAndView changeProfilePicture(@Valid @ModelAttribute("changeProfilePictureForm") final FormChangePicture changeProfilePictureForm ,
			final BindingResult errors, @ModelAttribute("loggedUser") final User loggedUser,
			RedirectAttributes attr) {
		
		ModelAndView mav = new ModelAndView("redirect:/profile/" + loggedUser.getUserId());
		
		if (errors.hasErrors()) {
			setErrorState(changeProfilePictureForm, errors, attr, loggedUser);
			return mav;
		}
		
		try {
			userService.changeProfilePicture(loggedUser.getUserId(), changeProfilePictureForm.getProfilePicture().getBytes());
		} catch (IOException e) {
			LOGGER.error("Failed to load profile picture: {}", e.getMessage());
			e.printStackTrace();
		}
		
		return mav;	
	}

	private void setErrorState(FormChangePicture changeProfilePictureForm, BindingResult errors, RedirectAttributes attr, User loggedUser) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.changeProfilePictureForm", errors);
		attr.addFlashAttribute("changeProfilePictureForm", changeProfilePictureForm);
		attr.addFlashAttribute("imgError", true);
	}
}

