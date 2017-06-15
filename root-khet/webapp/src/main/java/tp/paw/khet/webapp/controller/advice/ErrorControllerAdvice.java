package tp.paw.khet.webapp.controller.advice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.controller.mav.ErrorMav;
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
	
	@Autowired
	private ProductService productService;

	private ErrorMav buildErrorMav(final String errorPageTitle, final String errorTitle, final String errorCause, final String errorDesc) {
		final ErrorMav errorMav = new ErrorMav(errorPageTitle, errorTitle, errorCause, errorDesc);
		errorMav.addObject("loggedUser", securityUserService.getLoggedInUser());
		errorMav.addObject("topProducts", productService.getPlainProductsPaged(Optional.empty(), ProductSortCriteria.POPULARITY, 1, ProductControllerAdvice.TOP));
		return errorMav;		
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView resourceNotFound() {
		return new ModelAndView("redirect:/errors/400");
	}
	
    @ExceptionHandler(InvalidQueryException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ModelAndView invalidQuery() {
		return buildErrorMav("error.title.400badQuery", "error.400badQuery", "error.badQuery", "error.badQueryDesc");
    }
	
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView productNotFound() {
		return buildErrorMav("error.title.404product", "error.404product", "error.productNotFound", "error.productNotFoundDesc");
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView userNotFound() {
		return buildErrorMav("error.title.404user", "error.404user", "error.userNotFound", "error.userNotFoundDesc");
	}
	
	@ExceptionHandler(ImageNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public ModelAndView imageNotFound() {
		return buildErrorMav("error.title.404image", "error.404image", "error.imageNotFound", "error.imageNotFoundDesc");
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public ModelAndView unauthorized() {
		return new ModelAndView("redirect:/errors/401");
	}
		
	@ExceptionHandler(ForbiddenException.class)
	@ResponseStatus(value=HttpStatus.FORBIDDEN)
	public ModelAndView Forbidden() {
		return new ModelAndView("redirect:/errors/403");
	}	
}
