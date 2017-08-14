package tp.paw.khet.webapp.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.dto.CollectionListDTO;
import tp.paw.khet.webapp.dto.ProductListDTO;
import tp.paw.khet.webapp.dto.UserDTO;
import tp.paw.khet.webapp.dto.form.FormPicture;
import tp.paw.khet.webapp.dto.form.FormUser;
import tp.paw.khet.webapp.exception.DTOValidationException;
import tp.paw.khet.webapp.utils.PaginationLinkFactory;
import tp.paw.khet.webapp.validators.DTOConstraintValidator;

@Path("users")
@Controller
@Produces(value = {MediaType.APPLICATION_JSON})
public class UsersController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
	public static final int MAX_PAGE_SIZE = 100;
	public static final int DEFAULT_PAGE_SIZE = 20;

	@Autowired
	private SecurityUserService securityUserService;
	
    @Autowired
    private UserService userService;

	@Autowired
	private PaginationLinkFactory linkFactory;
	
	@Autowired
	private DTOConstraintValidator DTOValidator;
	
	@Context
	private UriInfo uriContext;
    
    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") final int id) {
        final User user = userService.getUserById(id);
        
        LOGGER.debug("Accessed getUserById with id {}", id);
        
        if (user != null) {
            return Response.ok(new UserDTO(user, uriContext.getBaseUri())).build();
        } else {
        	LOGGER.warn("Cannot render user profile, user with id {} not found", id);
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}/collections")
    public Response getUserCollections(
    		@PathParam("id") final int id, 
    		@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue("" + DEFAULT_PAGE_SIZE) @QueryParam("per_page") int pageSize) {
    	
    	LOGGER.debug("Accessed getUserCollections with id {}", id);
    	
    	page = nonNegativePage(page);
    	pageSize = validPageSizeRange(pageSize);
    	
    	final User user = userService.getUserById(id);
    	
    	if (user == null) {
    		LOGGER.warn("Cannot render user collections, user with id {} not found", id);
    		return Response.status(Status.NOT_FOUND).build();    		
    	}
    	
    	final int maxPage = userService.getMaxFavListsPageWithSize(id, pageSize);
    	final List<FavList> favLists = userService.getFavListsByUserId(id, page, pageSize);
    	final Link[] linkArray = linkFactory.createLinks(uriContext, page, maxPage).values().toArray(new Link[0]);
    	
        return Response.ok(new CollectionListDTO(favLists, uriContext.getBaseUri(), 
        		Optional.ofNullable(securityUserService.getLoggedInUser()))).links(linkArray).build();
    }

	@GET
    @Path("/{id}/voted_products")
    public Response getUserVotedProducts(
    		@PathParam("id") final int id,
       		@DefaultValue("1") @QueryParam("page") int page,
    		@DefaultValue("" + DEFAULT_PAGE_SIZE) @QueryParam("per_page") int pageSize) {
		
    	LOGGER.debug("Accessed getUserVotedProducts with id {}", id);

    	final User user = userService.getUserById(id);
    	
    	if (user == null) {
    		LOGGER.debug("Failed to get user with ID: {} voted products, user not found");
            return Response.status(Status.NOT_FOUND).build();
    	}

    	final int maxPage = userService.getMaxVotedProductsPageWithSize(id, pageSize);
    	final List<Product> votedProducts = userService.getVotedProductsByUserId(id, page, pageSize);
    	final Link[] linkArray = linkFactory.createLinks(uriContext, page, maxPage).values().toArray(new Link[0]);
    	    	
    	return Response.ok(new ProductListDTO(votedProducts, uriContext.getBaseUri(), 
    			Optional.ofNullable(securityUserService.getLoggedInUser()))).links(linkArray).build();
    }
    
    @GET
    @Path("/{id}/created_products")
    public Response getUserCreatedProducts(
    		@PathParam("id") final int id,
       		@DefaultValue("1") @QueryParam("page") int page,
    		@DefaultValue("" + DEFAULT_PAGE_SIZE) @QueryParam("per_page") int pageSize) {

    	LOGGER.debug("Accessed getUserCreatedProducts with id {}", id);

    	final User user = userService.getUserById(id);
    	
    	if (user == null) {
    		LOGGER.debug("Failed to get user with ID: {} created products, user not found");
            return Response.status(Status.NOT_FOUND).build();
    	}
 
    	final int maxPage = userService.getMaxCreatedProductsPageWithSize(id, pageSize);
    	final List<Product> createdProducts = userService.getCreatedProductsByUserId(id, page, pageSize);
    	final Link[] linkArray = linkFactory.createLinks(uriContext, page, maxPage).values().toArray(new Link[0]);
    	    	
    	return Response.ok(new ProductListDTO(createdProducts, uriContext.getBaseUri(), 
    			Optional.ofNullable(securityUserService.getLoggedInUser()))).links(linkArray).build();
    }
    
    @GET
    @Path("/{id}/picture")
    @Produces(value = {"image/png", "image/jpeg"})
    public Response getUserProfilePicture(@PathParam("id") final int id) {    	
    	LOGGER.debug("Accessed getUserProfilePicture with id {}", id);

    	final byte[] picture = userService.getProfilePictureByUserId(id);
		
		if (picture.length == 0) {
			LOGGER.warn("Cannot render user profile picture, user with id {} not found", id);
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(picture).build();
    }
    
    @POST
    @Path("/")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createUser(@FormDataParam("user") final FormUser formUser, @BeanParam final FormPicture formPicture) 
    		throws DuplicateEmailException, DTOValidationException {
    	
    	LOGGER.debug("Accessed createUser");
    	
		// @FormDataParam parameter is optional --> it may be null
		if (formUser == null)
			return Response.status(Status.BAD_REQUEST).build();
    	
    	DTOValidator.validate(formUser, "Failed to validate user");
    	DTOValidator.validate(formPicture, "Failed to validate picture");
    	
    	final User user = userService.createUser(formUser.getName(), formUser.getEmail(), formUser.getPassword(), formPicture.getPictureBytes());
		final URI location = uriContext.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();

    	return Response.created(location).entity(new UserDTO(user, uriContext.getBaseUri())).build();
    }
    
    private int nonNegativePage(int page) {
		return (page < 1) ? 1 : page;
	}

	private int validPageSizeRange(int pageSize) {
		return pageSize = (pageSize < 1 || pageSize > MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : pageSize; 
	}
}
