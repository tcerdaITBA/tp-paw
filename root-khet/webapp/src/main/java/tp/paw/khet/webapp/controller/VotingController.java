package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.VoteService;
import tp.paw.khet.webapp.exception.ProductNotFoundException;

@Controller
public class VotingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VotingController.class);
	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value= "/vote/product/{productId}", method = RequestMethod.POST)
	public ModelAndView voteProduct(@PathVariable final int productId, 
			@ModelAttribute("loggedUser") final User loggedUser,  final RedirectAttributes attr,
			@RequestHeader(value = "referer", required = false, defaultValue = "/") final String referrer) 
					throws ProductNotFoundException{
		
		final Product product = productService.getPlainProductById(productId);
		
		if (product == null) {
			LOGGER.warn("Failed to vote product with id {}: product not found");
			throw new ProductNotFoundException();
		}
		
		voteService.toggleVoteFromProduct(productId, loggedUser.getUserId());
		
		LOGGER.debug("User {} voted product with id {}", loggedUser.getUserId(), productId);
		
		String redirect = "redirect:" + referrer;
		
		attr.addFlashAttribute("productVoted", product.getId());
		
		return new ModelAndView(redirect);		
	}
}
