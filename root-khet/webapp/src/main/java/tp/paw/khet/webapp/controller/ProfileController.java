package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.exception.UnauthorizedException;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.exception.ProductNotFoundException;
import tp.paw.khet.webapp.exception.UserNotFoundException;
import tp.paw.khet.webapp.form.FormChangePassword;
import tp.paw.khet.webapp.form.FormChangePicture;

@Controller
@SessionAttributes(value={"changePasswordForm","changeProfilePictureForm"})
public class ProfileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);
	
    @Autowired 
    private UserService userService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SecurityUserService securityUserService;

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView userNotFound() {
		ModelAndView mav = new ModelAndView("404user");
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView productNotFound() {
		ModelAndView mav = new ModelAndView("404product");
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;
	}
	
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public ModelAndView Forbidden() {
		ModelAndView mav = new ModelAndView("403");
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public ModelAndView Unauthorized() {
		ModelAndView mav = new ModelAndView("401");
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;
	}
	
	@ModelAttribute("changePasswordForm")
	public FormChangePassword passwordForm(@ModelAttribute("loggedUser") final User loggedUser){
		return new FormChangePassword();
	}
	
	@ModelAttribute("changeProfilePictureForm")
	public FormChangePicture pictureForm(@ModelAttribute("loggedUser") final User loggedUser){
		return new FormChangePicture();
	}
	
	@RequestMapping("/profile/{userId}")
	public ModelAndView user(@PathVariable final int userId) throws UserNotFoundException {
		LOGGER.debug("Accessed user profile with ID: {}", userId);
		
		ModelAndView mav = new ModelAndView("profile");
		User user = userService.getUserById(userId);
				
		if (user == null) {
			LOGGER.warn("Cannot render user profile: user ID not found: {}", userId);
			throw new UserNotFoundException();
		}
				
		mav.addObject("profileUser", user);
		mav.addObject("products", productService.getPlainProductsByUserId(userId));
		return mav;
	}
	
	@RequestMapping(value = "/delete/product/{productId}", method = RequestMethod.POST)
	public ModelAndView deleteProduct(@PathVariable final int productId, @ModelAttribute("loggedUser") final User loggedUser, 
			@RequestHeader(value = "referer", required = false, defaultValue = "/") final String referrer,
			final RedirectAttributes attr)
	throws ProductNotFoundException, ForbiddenException, UnauthorizedException {
		
		LOGGER.debug("Accessed delete product POST for product with id: {} from {}", productId, referrer);
		
		final Product product = productService.getFullProductById(productId);
		
		if (product == null) {
			LOGGER.warn("Failed to delete product with id {}: product not found", productId);
			throw new ProductNotFoundException();
		}
		
		final User productCreator = product.getCreator();
		
		if (loggedUser == null) {
			LOGGER.warn("Failed to delete product with id {}: no user logged", productId);
			throw new UnauthorizedException();
		}
		
		if (!loggedUser.equals(productCreator)) {
			LOGGER.warn("Failed to delete product with id {}: logged user with id {} is not product creator with id {}", 
					productId, loggedUser.getUserId(), productCreator.getUserId());
			throw new ForbiddenException();
		}
	
		if (productService.deleteProductById(productId))
			LOGGER.info("Product with id {} deleted by user with id {}", productId, loggedUser.getUserId());
		
		String redirect = "redirect:";
		if (referrer.contains("product/" + productId))
			redirect += "/";
		else
			redirect += "/profile/" + product.getCreator().getUserId();
		
		attr.addFlashAttribute("productDeleted", product.getName());
		return new ModelAndView(redirect);
	}
	
	@ResponseBody
	@RequestMapping(value = "/profile/{userId}/profilePicture", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverProfilePicture(@PathVariable(value = "userId") final int userId) throws UserNotFoundException {
		
		User user = userService.getUserById(userId);
		
		if (user == null) {
			LOGGER.warn("Cannot render user profile picture: user ID not found: {}", userId);
			throw new UserNotFoundException();
		}
		
		return userService.getProfilePictureByUserId(userId);
	}
}
