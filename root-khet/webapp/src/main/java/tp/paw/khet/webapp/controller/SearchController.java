package tp.paw.khet.webapp.controller;

import java.util.Stack;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.model.Category;
import tp.paw.khet.service.HistoryService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.exception.InvalidQueryException;

public class SearchController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	private static final int MIN_QUERY = 3;
	private static final int MAX_QUERY = 64;
	private static final int MAX_RESULTS = 15;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private HistoryService historyService;

	@RequestMapping("/search")
	public ModelAndView searchResults(@RequestParam(value = "query") String query, HttpSession session)
			throws InvalidQueryException {
		LOGGER.debug("Accessed search with query {}", query);

		if (query == null || query.length() < MIN_QUERY) {
			LOGGER.warn("Invalid query: too short");
			throw new InvalidQueryException();
		} else if (query.length() > MAX_QUERY) {
			LOGGER.warn("Invalid query: too long");
			throw new InvalidQueryException();
		}

		@SuppressWarnings("unchecked")
		Stack<String> history = (Stack<String>) session.getAttribute("searchHistory");
		history = historyService.saveQueryInHistory(history, query);
		session.setAttribute("searchHistory", history);

		final ModelAndView mav = new ModelAndView("search-results");
		mav.addObject("products", productService.getPlainProductsByKeyword(query, 1, MAX_RESULTS));
		mav.addObject("categories", Category.values());
		mav.addObject("users", userService.getUsersByKeyword(query, 1, MAX_RESULTS));
		mav.addObject("queryText", query);
		return mav;
	}

}
