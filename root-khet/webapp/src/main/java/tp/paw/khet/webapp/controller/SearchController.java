package tp.paw.khet.webapp.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Category;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.exception.InvalidQueryException;

@Controller
public class SearchController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	private static final int MIN_QUERY = 3;
	private static final int MAX_QUERY = 64;
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    private static final int MAX_RESULTS = 10;
    
    @RequestMapping("/search")
    public ModelAndView searchResults(@RequestParam(value = "query") String query, HttpSession session) throws InvalidQueryException {
		LOGGER.debug("Accessed search with query {}", query);
    	
        if (query == null || query.length() < MIN_QUERY) {
        	LOGGER.warn("Invalid query: too short");
            throw new InvalidQueryException();
        }
        else if (query.length() > MAX_QUERY) {
        	LOGGER.warn("Invalid query: too long");
            throw new InvalidQueryException();      	
        }
        
        // TODO: ver que usar. No puede haber repetidos, pero tienen que estar ordenados.
        List<String> history = (List<String>) session.getAttribute("searchHistory");
        
        // TODO: donde se configura esto?
        session.setMaxInactiveInterval(0); // Trata de que la sesión no expire nunca.
        
        // TODO: ver valor por defecto de session attribute
        if (history == null) {
            LOGGER.debug("No search history. Creating new one.");
            history = new ArrayList<String>();
        }
        
        LOGGER.debug("Adding {} to search history", query);
        history.add(query);
        session.setAttribute("searchHistory", history);
        
        final ModelAndView mav = new ModelAndView("search-results");
        mav.addObject("products", productService.getPlainProductsByKeyword(query, MAX_RESULTS));
        mav.addObject("categories", Category.values());
        mav.addObject("users", userService.getUsersByKeyword(query, MAX_RESULTS));
        mav.addObject("queryText", query);
        return mav;
    }

}
