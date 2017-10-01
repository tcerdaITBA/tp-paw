package tp.paw.khet.webapp.rest;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.model.User;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.dto.ExceptionDTO;
import tp.paw.khet.webapp.dto.UserDTO;
import tp.paw.khet.webapp.dto.form.FormChangePassword;
import tp.paw.khet.webapp.dto.form.FormPicture;
import tp.paw.khet.webapp.exception.DTOValidationException;
import tp.paw.khet.webapp.exceptionmapper.ValidationMapper;
import tp.paw.khet.webapp.validators.DTOConstraintValidator;

@Path("user")
@Controller
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticatedUserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatedUserController.class);
	
	@Context
	private UriInfo uriContext;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DTOConstraintValidator DTOValidator;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GET
	@Path("/")
	public Response getAuthenticatedUser() {
		LOGGER.debug("Accessed getAuthenticatedUser");
		
		final User authenticatedUser = securityUserService.getLoggedInUser();
		
		return Response.ok(new UserDTO(authenticatedUser, uriContext.getBaseUri())).build();
	}
	
	@PUT
	@Path("/password")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changePassword(final FormChangePassword changePasswordForm) throws DTOValidationException {
		LOGGER.debug("Accessed change password");
		
		DTOValidator.validate(changePasswordForm, "Failed to validate change password form");
		
		final User authenticatedUser = securityUserService.getLoggedInUser();
		
		if (!passwordEncoder.matches(changePasswordForm.getCurrentPassword(), authenticatedUser.getPassword()))
			return Response.status(ValidationMapper.UNPROCESSABLE_ENTITY).entity(new ExceptionDTO("Incorrect password")).build();
		
		securityUserService.changePassword(authenticatedUser.getUserId(), changePasswordForm.getNewPassword());
		
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/picture")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response changePicture(@BeanParam final FormPicture picture) throws DTOValidationException {
		LOGGER.debug("Accessed change picture");
		
		DTOValidator.validate(picture, "Failed to validate picture");

		final User authenticatedUser = securityUserService.getLoggedInUser();

		userService.changeProfilePicture(authenticatedUser.getUserId(), picture.getPictureBytes());
		
		return Response.noContent().build();
	}
}
