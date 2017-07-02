package tp.paw.khet.webapp.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.dto.CollectionListDTO;
import tp.paw.khet.webapp.dto.ProductListDTO;
import tp.paw.khet.webapp.dto.UserDTO;

@Path("users")
@Controller
@Produces(value = {MediaType.APPLICATION_JSON})
public class UsersController {
    
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
        
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
    public Response getUserCollections(@PathParam("id") final int id) {
    	LOGGER.debug("Accessed getUserCollections with id {}", id);
    	
    	final User user = userService.getUserById(id);
    	
        if (user != null) {
        	final List<FavList> favLists = new ArrayList<>(user.getFavLists());
            return Response.ok(new CollectionListDTO(favLists, uriContext.getBaseUri())).build();
        } else {
        	LOGGER.warn("Cannot render user collections, user with id {} not found", id);
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Path("/{id}/voted_products")
    public Response getUserVotedProducts(@PathParam("id") final int id) {
    	LOGGER.debug("Accessed getUserVotedProducts with id {}", id);

    	final User user = userService.getUserById(id);
    	
    	if (user == null) {
    		LOGGER.debug("Failed to get user with ID: {} voted products, user not found");
            return Response.status(Status.NOT_FOUND).build();
    	}

    	final List<Product> votedProducts = new ArrayList<>(user.getVotedProducts());
    	
    	return Response.ok(new ProductListDTO(votedProducts, uriContext.getBaseUri())).build();
    }
    
    @GET
    @Path("/{id}/created_products")
    public Response getUserCreatedProducts(@PathParam("id") final int id) {
    	LOGGER.debug("Accessed getUserCreatedProducts with id {}", id);

    	final User user = userService.getUserById(id);
    	
    	if (user == null) {
    		LOGGER.debug("Failed to get user with ID: {} created products, user not found");
            return Response.status(Status.NOT_FOUND).build();
    	}
    	
    	final List<Product> createdProducts = productService.getPlainProductsByUserId(id);
    	
    	return Response.ok(new ProductListDTO(createdProducts, uriContext.getBaseUri())).build();
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
}
