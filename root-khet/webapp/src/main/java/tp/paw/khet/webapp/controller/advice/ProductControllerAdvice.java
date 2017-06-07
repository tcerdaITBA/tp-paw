package tp.paw.khet.webapp.controller.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import tp.paw.khet.model.Product;
import tp.paw.khet.service.ProductService;

@ControllerAdvice
public class ProductControllerAdvice {

	private static final int TOP = 3;
	
	@Autowired
	private ProductService productService;
	
	@ModelAttribute("topProducts")
	public List<Product> topProducts() {
		return productService.getPlainProductsPopularitySortedPaged(1, TOP);
	}
}