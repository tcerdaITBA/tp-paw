package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
			@ModelAttribute("loggedUser") final User loggedUser) throws ProductNotFoundException{
		
		LOGGER.debug("Voted product with id {}", productId);
		
		if(productService.getFullProductById(productId) == null){
			LOGGER.warn("Failed to vote product with id {}: product not found");
			throw new ProductNotFoundException();
		}
		
		voteService.voteProduct(productId, loggedUser.getUserId());
		
		return new ModelAndView("redirect:/product/" + productId);
		
	}
	
	@RequestMapping(value= "/unvote/product/{productId}", method = RequestMethod.POST)
	public ModelAndView unvoteProduct(@PathVariable final int productId, 
			@ModelAttribute("loggedUser") final User loggedUser) throws ProductNotFoundException{
		
		LOGGER.debug("Voted product with id {}", productId);
		
		if(productService.getFullProductById(productId) == null){
			LOGGER.warn("Failed to Unvote product with id {}: product not found");
			throw new ProductNotFoundException();
		}
		
		voteService.unvoteProduct(productId, loggedUser.getUserId());
		
		return new ModelAndView("redirect:/product/" + productId);
		
	}
	
}
