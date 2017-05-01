package tp.paw.khet.webapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.form.FormProduct;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;
import tp.paw.khet.webapp.validators.ImageOrVideoValidator;

@Controller
public class UploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@Autowired
	private ImageOrVideoValidator imageOrVideoValidator;
	
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
	
	@RequestMapping("/upload")
	public ModelAndView formCompletion(@ModelAttribute("uploadForm") final FormProduct product){
		ModelAndView mav = new ModelAndView("upload");
		mav.addObject("categories", Category.values());
		return mav;
	}
	
	@RequestMapping(value= "/upload", method = {RequestMethod.POST})
	public ModelAndView upload(@Valid @ModelAttribute("uploadForm") final FormProduct formProduct, final BindingResult errors,
							   @ModelAttribute("loggedUser") final User loggedUser) throws IOException {
		
		imageOrVideoValidator.validate(formProduct, errors);
		if (errors.hasErrors())
			return formCompletion(formProduct);
		
		final Product product =  productService.createProduct(formProduct.getName(), 
												formProduct.getDescription(), formProduct.getShortDescription(),
												formProduct.getWebsite(),
												formProduct.getCategory(),
												formProduct.getLogo().getBytes(), 
												loggedUser.getUserId(),
												imageByteList(formProduct.getImages()),
												videoIdList(formProduct.getVideos()));
		
		return new ModelAndView("redirect:/product/" + product.getId());
	}
		
	private List<String> videoIdList(VideoStringWrapper[] videos) {
		List<String> videoIdList = new ArrayList<>();
		for (VideoStringWrapper video : videos)
			if (video.hasUrl())
				videoIdList.add(video.getVideoId());
		return videoIdList;
	}

	private List<byte[]> imageByteList(MultipartFileImageWrapper[] images) {
		List<byte[]> byteList = new ArrayList<>();
		
		for (MultipartFileImageWrapper image : images) {
			if (image.hasFile()) {
				try {
					byteList.add(image.getFile().getBytes());
				} catch (IOException e) {
					LOGGER.error("Failed to get image's bytes");
					e.printStackTrace();
				}
			}
		}
		
		return byteList;
	}
	
}
