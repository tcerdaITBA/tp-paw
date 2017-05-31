package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping("/errors/400")
	public ModelAndView badRequest() {
		LOGGER.warn("Bad Request");
		return new ModelAndView("400");
	}
	
	@RequestMapping("/errors/401")
	public ModelAndView unauthorized() {
		LOGGER.warn("Unauthorized");
		return new ModelAndView("401");
	}
	
	@RequestMapping("/errors/403")
	public ModelAndView forbidden() {
		LOGGER.warn("Forbidden");
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/errors/404")
	public ModelAndView noSuchRequestHandler() {
		LOGGER.warn("Page not found");
		return new ModelAndView("404");
	}
	
	
}