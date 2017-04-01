package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.service.ProductService;

@Controller
public class HelloWorldController {
	
    @Autowired
    private ProductService productService;
    
	@RequestMapping("/")
	public ModelAndView helloWorld() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("products", productService.getProducts());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/logo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverLogo(@PathVariable(value = "productId") int productId) {
		return productService.getLogoByProductId(productId);
	}
}
