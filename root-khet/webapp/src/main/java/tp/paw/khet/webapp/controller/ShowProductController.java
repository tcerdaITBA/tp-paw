package tp.paw.khet.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Product;
import tp.paw.khet.Video;
import tp.paw.khet.service.ProductImageService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.VideoService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;

@Controller
public class ShowProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@RequestMapping(value = "/product/{productId}", method = {RequestMethod.GET})
	public ModelAndView getProduct(@PathVariable final int productId, HttpServletResponse response) 
	throws ResourceNotFoundException {
					
		Product product = productService.getProduct(productId);
		
		if (product == null) {
			throw new ResourceNotFoundException();
		}
		
		ModelAndView mav = new ModelAndView("product");
				
		mav.addObject("product", product);
		mav.addObject("images", productImageService.getImagesIdByProductId(product.getId()));
		mav.addObject("videos",videoService.getVideosByProductId(product.getId()));
		mav.addObject("user", productService.getCreatorByProductId(product.getId()));

		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/image/{imageId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] getProductImage(@PathVariable final int productId, @PathVariable final int imageId) {
		return productImageService.getImageByIds(imageId, productId).getData();
	}
}
