package tp.paw.khet.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Category;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;

@Controller
public class IndexController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
	private static final int PAGE_SIZE = 10; 
	
	@Autowired
    private ProductService productService;

	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "page", required = false, defaultValue = "1") int page) throws ResourceNotFoundException {
		LOGGER.debug("Accessed index with page {}", page);
		
	    final int maxPage = productService.getMaxProductPageWithSize(PAGE_SIZE);
	    
	    if (page < 1 || page > maxPage && maxPage > 0) {
	    	LOGGER.warn("Index page out of bounds: {}", page);
	        throw new ResourceNotFoundException();
	    }
	    
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("products", productService.getPlainProductsPaged(page, PAGE_SIZE));
		mav.addObject("categories", Category.values());
		mav.addObject("currentPage", page);
		mav.addObject("totalPages", maxPage);
		return mav;
	}
		
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request) {
		LOGGER.debug("Accessed login");
		
		String referrer = request.getHeader("Referer");
	    request.getSession().setAttribute("url_prior_login", referrer);
	    return new ModelAndView("login");
	}
}
