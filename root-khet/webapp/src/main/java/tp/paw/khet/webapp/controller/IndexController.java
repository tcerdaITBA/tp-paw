package tp.paw.khet.webapp.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.utils.CaseInsensitiveConverter;

@Controller
public class IndexController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	private static final int PAGE_SIZE = 10; 
	
	@Autowired
    private ProductService productService;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "page", required = false, defaultValue = "1") final int page, 
	        @RequestParam(value="orderBy", required = false, defaultValue = "recent") final ProductSortCriteria sortCriteria,
	        @RequestParam(value="category", required = false) final Optional<Category> category)
			throws ResourceNotFoundException {
	    
		LOGGER.debug("Accessed index with page {}", page);
		
	    final int maxPage = productService.getMaxProductPageWithSize(category, PAGE_SIZE);
	    
	    if (page < 1 || page > maxPage && maxPage > 0) {
	    	LOGGER.warn("Index page out of bounds: {}", page);
	        throw new ResourceNotFoundException();
	    }
	    
		final ModelAndView mav = new ModelAndView("index");
		
        mav.addObject("products", productService.getPlainProductsPaged(category, sortCriteria, page, PAGE_SIZE));		
        mav.addObject("productOrder", sortCriteria);
		mav.addObject("currentPage", page);
		mav.addObject("totalPages", maxPage);
		mav.addObject("categories", Category.values());
		
		if (category.isPresent())
			mav.addObject("currentCategory", category.get());
		
		return mav;
	}
		
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		LOGGER.debug("Accessed login");
		
		String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("url_prior_login", referrer);
	    return new ModelAndView("login");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(ProductSortCriteria.class, new CaseInsensitiveConverter<>(ProductSortCriteria.class));
		binder.registerCustomEditor(Category.class, new CaseInsensitiveConverter<>(Category.class));
	}
}
