package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import tp.paw.khet.Category;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.utils.CaseInsensitiveConverter;

@Controller
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	
	//TODO: sacar
	private static int PAGE_SIZE = 10; 

	@Autowired
    private ProductService productService;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView productNotFound() {
		ModelAndView mav = new ModelAndView("404");
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;
	}

	
	@RequestMapping(value = "/category/{category}")
	public ModelAndView showProductsForCategory(@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
	        @PathVariable(value = "category") Category category) {
		
		LOGGER.debug("Accessed category {} with page {}", category, page);
		
		int maxPage = productService.getMaxProductPageInCategoryWithSize(category, PAGE_SIZE);
	     
        if (page < 1 || page > maxPage && maxPage > 0) {
        	LOGGER.warn("Category page out of bounds: {}", page);
        	throw new ResourceNotFoundException();
        }
	    
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("currentCategory", category);
		mav.addObject("categories", Category.values());
	    mav.addObject("products", productService.getPlainProductsByCategoryPaged(category, page, PAGE_SIZE));
	    mav.addObject("currentPage", page);
	    mav.addObject("totalPages", maxPage);
	    return mav;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Category.class,new CaseInsensitiveConverter<>(Category.class));
	}

}
