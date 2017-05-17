package tp.paw.khet.webapp.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.exception.UnauthorizedException;
import tp.paw.khet.webapp.form.FormChangePassword;
import tp.paw.khet.webapp.form.FormPassword;
import tp.paw.khet.webapp.validators.PasswordConfirmValidator;
import tp.paw.khet.service.UserService;

@Controller
public class IndexController {
		
    @Autowired
    private ProductService productService;
    
    @Autowired 
    private UserService userService;
    
	@Autowired
	private SecurityUserService securityUserService;
	
	@Autowired
	private PasswordConfirmValidator passwordConfirmValidator;

    
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
	
	//TODO: sacar
    private static int PAGE_SIZE = 10; 
    
	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
	    int maxPage = productService.getMaxProductPageWithSize(PAGE_SIZE);
	    if (page < 1 || page > maxPage && maxPage > 0)
	        throw new ResourceNotFoundException();
	    
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("products", productService.getPlainProductsPaged(page, PAGE_SIZE));
		mav.addObject("categories", Category.values());
		mav.addObject("currentPage", page);
		mav.addObject("totalPages", maxPage);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/logo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverLogo(@PathVariable(value = "productId") int productId) {
		return productService.getLogoByProductId(productId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/profile/{userId}/profilePicture", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverProfilePicture(@PathVariable(value = "userId") int userId) {
		return userService.getProfilePictureByUserId(userId);
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
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
	throws ResourceNotFoundException, UnauthorizedException, ForbiddenException{
		Product product = productService.getFullProductById(productId);
		
		if (product == null) 
			throw new ResourceNotFoundException();
		
		User loggedUser = securityUserService.getLoggedInUser();
		User productCreator = product.getCreator();
		
		if (loggedUser == null)
			throw new UnauthorizedException();
		
		if (loggedUser.getUserId() != productCreator.getUserId())
				throw new ForbiddenException();
	
		productService.deleteProductById(productId);
		
		User owner = product.getCreator();
		
		return new ModelAndView("redirect:/profile/" + owner.getUserId());
	}
	
	@ModelAttribute("changePasswordForm")
	public FormChangePassword passwordForm(){
		return new FormChangePassword();
	}
		
	@RequestMapping(value = "/profile/customize/password", method = {RequestMethod.POST})
	public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") final FormChangePassword changePasswordForm,
			final BindingResult errors, @ModelAttribute("loggedUser") final User loggedUser,
			RedirectAttributes attr) {
		
		FormPassword passwordForm = changePasswordForm.getPasswordForm();
		
		errors.pushNestedPath("passwordForm");
		passwordConfirmValidator.validate(passwordForm, errors);
		errors.popNestedPath();
		
		if (errors.hasErrors())
			return errorState(loggedUser.getUserId(), changePasswordForm, errors, attr, loggedUser);
		
		securityUserService.changePassword(loggedUser.getUserId(), passwordForm.getPassword());
		
		return new ModelAndView("redirect:/profile/" + loggedUser.getUserId());	
	}

	private ModelAndView errorState(int userId,FormChangePassword changePasswordForm, BindingResult errors, RedirectAttributes attr, User loggedUser) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordForm", errors);
		attr.addFlashAttribute("changePasswordForm", changePasswordForm);
		return new ModelAndView("redirect:/profile/" + userId + "?passwordError=1");	
	}

}
