package tp.paw.khet.webapp.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

// http://stackoverflow.com/questions/14573654/spring-security-redirect-to-previous-page-after-successful-login

public class RefererLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RefererLoginSuccessHandler.class);
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String redirectUrl;
        
        if (session != null && (redirectUrl = (String) session.getAttribute("url_prior_login")) != null) {
        	LOGGER.debug("redirectUrl: {}", redirectUrl);
        	session.removeAttribute("url_prior_login");
        	getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
