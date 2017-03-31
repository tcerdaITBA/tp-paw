package tp.paw.khet.webapp.controller;


import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	public ModelAndView upload(@Valid @ModelAttribute("uploadForm") final FormProduct formProduct,
										final BindingResult errors //,
										//@RequestParam("logo") MultipartFile logo,
										/*@RequestParam("image") MultipartFile image*/) throws IOException {
		
		if(errors.hasErrors()){
			return formCompletion(formProduct);
		}
//		final Product prod =  productService.createProduct(formProduct.getName(), 
//												formProduct.getDescription(), formProduct.getShortDescription(),
//												logo.getBytes());
		return new ModelAndView("submitted");
	}
	
}
