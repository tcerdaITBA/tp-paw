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
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.VideoService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;

@Controller
public class ShowProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private VideoService videoService;
	
	private Product product;
	
	@RequestMapping(value = "/product/{productId}", method = {RequestMethod.GET})
	public ModelAndView getProduct(@PathVariable final int productId, HttpServletResponse response) 
	throws ResourceNotFoundException {
				
		product = productService.getProduct(productId);
		
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		
		return new ModelAndView("submitted");
	}
	
	@ModelAttribute("product")
	public Product getProduct() {
		return (product == null) ? null : product;
	}
	
	@ModelAttribute("video")
	public String getVideo() {
		return "y5cbEwrsK7o";
	}
	

	// matias, mtsperazzo@itba.edu.ar, id=3
			
	// producto, "asd", "probando", CDRIVES, del 4-7 (id)
	 
}
