package tp.paw.khet.webapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.*;

import tp.paw.khet.Product;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.ProductServiceImpl;
import tp.paw.khet.webapp.form.FormProduct;

@Controller
public class UploadController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/upload")
	public ModelAndView formCompletion(@ModelAttribute("uploadForm") final FormProduct product){
		return new ModelAndView("upload");
	}
	
	@RequestMapping(value= "/upload", method = {RequestMethod.POST})
	public ModelAndView upload(@ModelAttribute("uploadForm") final FormProduct formProduct) {
//		final Product prod =  productService.createProduct(formProduct.getName(), 
//												formProduct.getDescription(), formProduct.getShortDescription(),
//												formProduct.logo());
		return new ModelAndView("submitted");
	}
	
}
