package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.service.ProductService;

@Controller
public class HelloWorldController {
	
    @Autowired
    ProductService productService;
    
	@RequestMapping("/")
	public ModelAndView helloWorld() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("products", productService.getProducts());
		return mav;
	}
}
