package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import tp.paw.khet.Category;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.utils.CaseInsensitiveConverter;

@Controller
public class CategoryController {

	@Autowired
    private ProductService productService;
	
	//TODO: sacar
    private static int PAGE_SIZE = 1; 

    @Autowired
	private SecurityUserService securityUserService;
	
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
	
	@RequestMapping(value = "/category/{category}")
	public ModelAndView showProductsForCategory(@RequestParam(value = "page", required = false, defaultValue = "1") int page, 
	        @PathVariable(value = "category") Category category) {
	     int maxPage = productService.getMaxProductPageInCategoryWithSize(category, PAGE_SIZE);
         if (page < 1 || page > maxPage && maxPage > 0)
            throw new ResourceNotFoundException();
	    
		 ModelAndView mav = new ModelAndView("index");
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
