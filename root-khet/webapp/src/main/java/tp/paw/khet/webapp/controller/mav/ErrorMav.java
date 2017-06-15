package tp.paw.khet.webapp.controller.mav;

import org.springframework.web.servlet.ModelAndView;

public class ErrorMav extends ModelAndView {
	
	public ErrorMav(final String errorPageTitle, final String errorTitle, final String errorCause, final String errorDesc) {
		super("error");
		addObject("errorPageTitle", errorPageTitle);
		addObject("errorTitle", errorTitle);
		addObject("errorCause", errorCause);
		addObject("errorDesc", errorDesc);
	}
}
