package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Category;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;

@Controller
public class IndexController {
		
    @Autowired
    private ProductService productService;
    
    //TODO sacar
    private static int PAGE_SIZE = 1; 
    
	@RequestMapping("/")
	public ModelAndView index(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
	    int maxPage = productService.getMaxProductPageWithSize(PAGE_SIZE);
	    if (page < 1 || page > maxPage && maxPage > 0)
	        throw new ResourceNotFoundException();
	    
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("products", productService.getPlainProductsPaged(page, PAGE_SIZE));
		mav.addObject("categories", Category.values());
		mav.addObject("currentPage", page);
		mav.addObject("totalPages", maxPage);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/logo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverLogo(@PathVariable(value = "productId") int productId) {
		return productService.getLogoByProductId(productId);
	}
	
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}
