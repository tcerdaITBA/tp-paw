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

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.service.ProductImageService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.service.VideoService;
import tp.paw.khet.webapp.form.FormProduct;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;
import tp.paw.khet.webapp.validators.EqualsUsernameValidator;
import tp.paw.khet.webapp.validators.ImageOrVideoValidator;

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
	
	@Autowired
	private ImageOrVideoValidator imageOrVideoValidator;
	
	@Autowired 
	private EqualsUsernameValidator equalsUsernameValidator;
	
	@RequestMapping("/upload")
	public ModelAndView formCompletion(@ModelAttribute("uploadForm") final FormProduct product){
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("categories", Category.values());
		return mav;
	}
	
	@RequestMapping(value= "/upload", method = {RequestMethod.POST})
	public ModelAndView upload(@Valid @ModelAttribute("uploadForm") final FormProduct formProduct,
										final BindingResult errors) throws IOException {
		
		imageOrVideoValidator.validate(formProduct, errors);
		if (errors.hasErrors())
			return formCompletion(formProduct);
		
		final User user = userService.createUserOrRetrieveIfExists(formProduct.getUserName(), formProduct.getUserEmail());
		
		equalsUsernameValidator.validate(EqualsUsernameValidator.buildUserNamePair(formProduct.getUserName(), user.getName()), errors);
		if (errors.hasErrors())
			return formCompletion(formProduct);
		
		final Product product =  productService.createProduct(formProduct.getName(), 
												formProduct.getDescription(), formProduct.getShortDescription(),
												formProduct.getWebsite(),
												formProduct.getCategory(),
												formProduct.getLogo().getBytes(), user.getUserId());
		
		storeImages(formProduct.getImages(), product.getId());
		storeVideos(formProduct.getVideos(), product.getId());
		
		return new ModelAndView("redirect:/product/" + product.getId());
	}
		
	private void storeVideos(VideoStringWrapper[] videos, int productId) {		
		for (VideoStringWrapper video : videos)
			if (video.hasUrl())
				videoService.createVideo(video.getVideoId(), productId);
	}

	private void storeImages(MultipartFileImageWrapper[] images, int productId) throws IOException {
		int j = 0;
		
		for (MultipartFileImageWrapper image : images)
			if (image.hasFile())
				productImageService.createProductImage(j++, productId, image.getFile().getBytes());
	}
	
}
