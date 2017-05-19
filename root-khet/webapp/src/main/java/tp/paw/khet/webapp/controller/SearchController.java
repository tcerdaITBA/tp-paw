package tp.paw.khet.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;

@Controller
public class SearchController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    private static final int MAX_RESULTS = 10;
    
    @RequestMapping("/search")
    public ModelAndView searchResults(@RequestParam(value = "query") String query) {
        if (query == null || query.length() < 3);
            // TODO: excepcion?
        
        ModelAndView mav = new ModelAndView("search-results");
        mav.addObject("products", productService.getPlainProductsByKeyword(query, MAX_RESULTS));
        mav.addObject("users", userService.getUsersByKeyword(query, MAX_RESULTS));
        mav.addObject("queryText", query);
        return mav;
    }

}
