package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.exception.UnauthorizedException;
import tp.paw.khet.webapp.form.FormChangePassword;
import tp.paw.khet.webapp.form.FormChangePicture;

@Controller
@SessionAttributes(value={"changePasswordForm","changeProfilePictureForm"})
public class ProfileController {
	
    @Autowired 
    private UserService userService;

    @Autowired
    private ProductService productService;

	@Autowired
	private SecurityUserService securityUserService;
	
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}

	@ModelAttribute("changePasswordForm")
	public FormChangePassword passwordForm(@ModelAttribute("loggedUser") User loggedUser){
		return new FormChangePassword();
	}
	
	@ModelAttribute("changeProfilePictureForm")
	public FormChangePicture pictureForm(@ModelAttribute("loggedUser") User loggedUser){
		return new FormChangePicture();
	}
	
	@RequestMapping("/profile/{userId}")
	public ModelAndView user(@PathVariable final int userId) throws ResourceNotFoundException {
		ModelAndView mav = new ModelAndView("profile");
		
		User user = userService.getUserById(userId);
				
		if (user == null)
			throw new ResourceNotFoundException();
		
		User loggedUser = securityUserService.getLoggedInUser();
		
		mav.addObject("loggedUser", loggedUser);
		mav.addObject("profileUser", user);
		mav.addObject("us", userService.getUserById(userId));
		mav.addObject("products", productService.getPlainProductsByUserId(userId));
		return mav;
	}
	
	@RequestMapping(value = "/delete/product/{productId}", method = RequestMethod.POST)
	public ModelAndView deleteProduct(@PathVariable final int productId)
	throws ResourceNotFoundException, UnauthorizedException, ForbiddenException {
		Product product = productService.getFullProductById(productId);
		
		if (product == null) 
			throw new ResourceNotFoundException();
		
		User loggedUser = securityUserService.getLoggedInUser();
		User productCreator = product.getCreator();
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		
		if (!loggedUser.equals(productCreator))
			throw new ForbiddenException();
	
		productService.deleteProductById(productId);
		
		User owner = product.getCreator();
		
		return new ModelAndView("redirect:/profile/" + owner.getUserId());
	}
	
	@ResponseBody
	@RequestMapping(value = "/profile/{userId}/profilePicture", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverProfilePicture(@PathVariable(value = "userId") int userId) {
		return userService.getProfilePictureByUserId(userId);
	}
}
