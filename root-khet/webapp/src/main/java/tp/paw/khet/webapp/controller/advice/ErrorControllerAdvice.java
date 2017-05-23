package tp.paw.khet.webapp.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.exception.ImageNotFoundException;
import tp.paw.khet.webapp.exception.InvalidQueryException;
import tp.paw.khet.webapp.exception.ProductNotFoundException;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.exception.UnauthorizedException;
import tp.paw.khet.webapp.exception.UserNotFoundException;

@ControllerAdvice
public class ErrorControllerAdvice {

	@Autowired
	private SecurityUserService securityUserService;

	private ModelAndView buildModelAndView(String jspName) {
		ModelAndView mav = new ModelAndView(jspName);
		mav.addObject("loggedUser", securityUserService.getLoggedInUser());
		return mav;		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView resourceNotFound() {
		return buildModelAndView("404");
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView productNotFound() {
		return buildModelAndView("404product");
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView userNotFound() {
		return buildModelAndView("404user");
	}
	
	@ExceptionHandler(ImageNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView imageNotFound() {
		return buildModelAndView("404image");
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public ModelAndView unauthorized() {
		return buildModelAndView("401");
	}
		
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public ModelAndView Forbidden() {
		return buildModelAndView("403");
	}
	
    @ExceptionHandler(InvalidQueryException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ModelAndView invalidQuery() {
		return buildModelAndView("400badQuery");
    }
}
