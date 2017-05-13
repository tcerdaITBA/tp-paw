package tp.paw.khet.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.form.FormPassword;
import tp.paw.khet.webapp.form.FormProduct;
import tp.paw.khet.webapp.validators.PasswordConfirmValidator;

@Controller
public class ProfileCustomizeController {

	 @Autowired
	    private ProductService productService;
	    
		@Autowired
		private SecurityUserService securityUserService;

	    
		@ModelAttribute("loggedUser")
		public User loggedUser() {
			return securityUserService.getLoggedInUser();
		}
		
		@ModelAttribute("changePasswordForm")
		public FormPassword passwordForm(){
			return new FormPassword();
		}
		
		@RequestMapping(value = "/profile/customize/password", method = {RequestMethod.GET})
		public ModelAndView changePassword(){
			return new ModelAndView("changePassword");
		}
		
		@RequestMapping(value = "/profile/customize/password", method = {RequestMethod.POST})
		public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") final FormPassword formPassword,
				final BindingResult errors, @ModelAttribute("loggedUser") final User loggedUser,
				RedirectAttributes attr){
			
			if (errors.hasErrors())
				return errorState(formPassword, errors, attr, loggedUser);
			
			securityUserService.changePassword(loggedUser.getUserId(), formPassword.getPassword());
			
			return new ModelAndView("redirect:/profile/" + loggedUser.getUserId());
			
			
		}

		private ModelAndView errorState(FormPassword formPassword, BindingResult errors, RedirectAttributes attr, User loggedUser) {
			attr.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", errors);
			attr.addFlashAttribute("changePasswordForm", formPassword);
			return new ModelAndView("redirect:/profile/customize/password");	
		}
}

