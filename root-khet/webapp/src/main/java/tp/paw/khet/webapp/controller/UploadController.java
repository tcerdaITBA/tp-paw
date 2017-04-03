package tp.paw.khet.webapp.controller;


import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.*;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.form.FormProduct;

@Controller
public class UploadController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/upload")
	public ModelAndView formCompletion(@ModelAttribute("uploadForm") final FormProduct product){
		return new ModelAndView("upload");
	}
	
	@RequestMapping(value= "/upload", method = {RequestMethod.POST})
	public ModelAndView upload(@Valid @ModelAttribute("uploadForm") final FormProduct formProduct,
										final BindingResult errors) throws IOException {
		
		if (errors.hasErrors())
			return formCompletion(formProduct);
		
		final User user = userService.createUser(formProduct.getCreatorName(), formProduct.getCreatorMail());
		
		final Product prod =  productService.createProduct(formProduct.getName(), 
												formProduct.getDescription(), formProduct.getShortDescription(),
												formProduct.getLogo().getBytes(), user.getUserId());
		
		return new ModelAndView("submitted");
	}
	
}
