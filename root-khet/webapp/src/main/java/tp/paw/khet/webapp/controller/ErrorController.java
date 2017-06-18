package tp.paw.khet.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.webapp.controller.mav.ErrorMav;

@Controller
public class ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping("/errors/400")
	public ModelAndView badRequest() {
		LOGGER.warn("Bad Request");
		return new ErrorMav("error.title.400", "error.400", "error.badRequest", "error.badRequestDesc");
	}
	
	@RequestMapping("/errors/401")
	public ModelAndView unauthorized() {
		LOGGER.warn("Unauthorized");
		return new ErrorMav("error.title.401", "error.401", "error.unAuthorized", "error.unAuthorizedDesc");
	}
	
	@RequestMapping("/errors/403")
	public ModelAndView forbidden() {
		LOGGER.warn("Forbidden");
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping("/errors/404")
	public ModelAndView noSuchRequestHandler() {
		LOGGER.warn("Page not found");
		return new ErrorMav("error.title.404", "error.404", "error.pageNotFound", "error.pageNotFoundDesc");
	}
}