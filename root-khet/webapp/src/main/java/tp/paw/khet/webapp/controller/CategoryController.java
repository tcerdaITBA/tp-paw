package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tp.paw.khet.Category;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.utils.CaseInsensitiveConverter;

@Controller
public class CategoryController {

	@Autowired
    private ProductService productService;
	
	@RequestMapping(value = "/category/{category}")
	public ModelAndView showProductsForCategory(@PathVariable(value = "category") Category category) {
		 ModelAndView mav = new ModelAndView("index");
		 mav.addObject("products",productService.getPlainProductsByCategory(category));
		 mav.addObject("categories", Category.values());
		 return mav;
	}

	@InitBinder
	 public void initBinder(WebDataBinder binder) {
	  binder.registerCustomEditor(Category.class,new CaseInsensitiveConverter<>(Category.class));
	 }
}
