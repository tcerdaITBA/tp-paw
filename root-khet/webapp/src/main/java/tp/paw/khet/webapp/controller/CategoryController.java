package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.model.Category;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.utils.CaseInsensitiveConverter;

@Controller
public class CategoryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
	private static final int PAGE_SIZE = 10; 
	
	@Autowired
    private ProductService productService;
	
	@RequestMapping(value = "/category/{category}")
	public ModelAndView showProductsForCategory(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
	        @RequestParam(value="orderBy", required = false, defaultValue = "recent") final String order,
	        @PathVariable(value = "category") Category category) throws ResourceNotFoundException {
		
		LOGGER.debug("Accessed category {} with page {}", category, page);
		
		final int maxPage = productService.getMaxProductPageInCategoryWithSize(category, PAGE_SIZE);
	     
        if (page < 1 || page > maxPage && maxPage > 0) {
        	LOGGER.warn("Category page out of bounds: {}", page);
        	throw new ResourceNotFoundException();
        }
	    
        ModelAndView mav = new ModelAndView("index");

        switch(order) {
            case "recent":
                mav.addObject("products", productService.getPlainProductsByCategoryPaged(category, page, PAGE_SIZE));
                break;
            case "popularity":
                mav.addObject("products", productService.getPlainProductsPopularitySortedByCategoryPaged(category, page, PAGE_SIZE));
                break;
            case "alphabethically":
                mav.addObject("products", productService.getPlainProductsAlphabeticallyByCategoryPaged(category, page, PAGE_SIZE));
                break;
        }
        mav.addObject("productOrder", order);
		mav.addObject("currentCategory", category);
		mav.addObject("categories", Category.values());
	    mav.addObject("currentPage", page);
	    mav.addObject("totalPages", maxPage);
	    return mav;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Category.class,new CaseInsensitiveConverter<>(Category.class));
	}

}
