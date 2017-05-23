package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Category;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.exception.InvalidQueryException;

@Controller
public class SearchController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
	@Autowired
	private SecurityUserService securityUserService;
    
    private static final int MAX_RESULTS = 10;
    
    @ExceptionHandler(InvalidQueryException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ModelAndView userNotFound() {
		ModelAndView mav = new ModelAndView("400badQuery");
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;
    }
    
    @RequestMapping("/search")
    public ModelAndView searchResults(@RequestParam(value = "query") String query) throws InvalidQueryException {
		LOGGER.debug("Accessed search with query {}", query);
    	
        if (query == null || query.length() < 3) {
        	LOGGER.warn("Invalid query, too short");
            throw new InvalidQueryException();
        }
        
        ModelAndView mav = new ModelAndView("search-results");
        mav.addObject("products", productService.getPlainProductsByKeyword(query, MAX_RESULTS));
        mav.addObject("categories", Category.values());
        mav.addObject("users", userService.getUsersByKeyword(query, MAX_RESULTS));
        mav.addObject("queryText", query);
        return mav;
    }

}
