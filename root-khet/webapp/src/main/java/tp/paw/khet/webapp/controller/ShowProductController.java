package tp.paw.khet.webapp.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Product;
import tp.paw.khet.Video;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.VideoService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;

@Controller
public class ShowProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private VideoService videoService;
	
	@RequestMapping(value = "/product/{productId}", method = {RequestMethod.GET})
	public ModelAndView getProduct(@PathVariable final int productId, HttpServletResponse response) 
	throws ResourceNotFoundException {
					
		Product product = productService.getProduct(productId);
		
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		
		ModelAndView mav = new ModelAndView("product");
		
		mav.addObject("product", product);
		mav.addObject("videos", videoService.getVideosByProductId(product.getId()));
		
		return mav;
	} 
}
