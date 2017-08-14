package tp.paw.khet.webapp.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.model.User;
import tp.paw.khet.webapp.dto.UserDTO;

@Path("user")
@Controller
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticatedUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUserController.class);
	
	@Context
	private UriInfo uriContext;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@GET
	@Path("/")
	public Response getAuthenticatedUser() {
		LOGGER.debug("Accessed getAuthenticatedUser");
		
		final User authenticatedUser = securityUserService.getLoggedInUser();
		
		return Response.ok(new UserDTO(authenticatedUser, uriContext.getBaseUri())).build();
	}
}
