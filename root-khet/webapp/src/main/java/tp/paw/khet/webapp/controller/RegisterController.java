package tp.paw.khet.webapp.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.webapp.form.FormUser;

@Controller
public class RegisterController {
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@RequestMapping(value = "/register", method = {RequestMethod.GET})
	public ModelAndView register(@ModelAttribute("createUserForm") FormUser formUser) {
		return new ModelAndView("createUser");
	}
	
	@RequestMapping(value = "/register", method = {RequestMethod.POST})
	public ModelAndView register(@ModelAttribute("createUserForm") @Valid FormUser formUser, BindingResult errors) throws IOException {
		
		if (errors.hasErrors())
			return register(formUser);
		
		securityUserService.registerUser(formUser.getName(), formUser.getEmail(), formUser.getPassword(), formUser.getProfilePicture().getBytes());
		
		return new ModelAndView("redirect:/");
	}
}