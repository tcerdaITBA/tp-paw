package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tp.paw.khet.Category;
import tp.paw.khet.service.ProductService;

@Controller
public class CategoryController {

	@Autowired
    private ProductService productService;
	
	@RequestMapping(value = "/category/{category}")
	public ModelAndView showProductsForCategory(@PathVariable(value = "category") final Category category) {
		 ModelAndView mav = new ModelAndView("category");
		 mav.addObject("products",productService.getProductsByCategory(category));
		 return mav;
	}

}
