package tp.paw.khet.webapp.controller;


import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.*;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.service.ProductImageService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.service.VideoService;
import tp.paw.khet.webapp.form.FormProduct;

@Controller
public class UploadController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private VideoService videoService;
	
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
		
		storeImages(formProduct.getImages(), prod.getId());
		
		return new ModelAndView("redirect:/product/" + prod.getId());
	}
	
	private void storeImages(MultipartFile[] images, int productId) throws IOException {
		int j = 0;
		for (int i = 0; i < images.length; i++) {
			if (!images[i].isEmpty())
				productImageService.createProductImage(j++, productId, images[i].getBytes());
		}
	}
	
}
