package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.exception.UnauthorizedException;
import tp.paw.khet.service.UserService;

@Controller
public class IndexController {
		
    @Autowired
    private ProductService productService;
    
    //TODO sacar
    private static int PAGE_SIZE = 1; 

    @Autowired 
    private UserService userService;
    
	@Autowired
	private SecurityUserService securityUserService;
    
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}

    
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
	public ModelAndView user(@PathVariable final int userId) {
		ModelAndView mav = new ModelAndView("profile");
		mav.addObject("us", userService.getUserById(userId));
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
		
		return new ModelAndView("redirect:/");
	}
	
}
