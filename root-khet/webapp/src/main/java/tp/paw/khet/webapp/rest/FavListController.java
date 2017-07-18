package tp.paw.khet.webapp.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.User;
import tp.paw.khet.service.FavListService;
import tp.paw.khet.webapp.dto.CollectionDTO;

@Path("collections")
@Controller
@Produces(MediaType.APPLICATION_JSON)
public class FavListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavListController.class);
	
	@Context
	private UriInfo uriContext;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@Autowired
	private FavListService favListService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response createCollection(final CollectionDTO collection) throws DuplicateFavListException {
		LOGGER.debug("Accessed createCollection");
		
		final User creator = securityUserService.getLoggedInUser();
		final FavList favList = favListService.createFavList(collection.getName(), creator.getUserId());
		final URI location = uriContext.getAbsolutePathBuilder().path(String.valueOf(favList.getId())).build();
				
		return Response.created(location).entity(new CollectionDTO(favList, uriContext.getBaseUri())).build();
	}
	
	@GET
	@Path("/{id}")
	public Response getCollection(@PathParam("id") final int id) {
		LOGGER.debug("Accessed getCollection with ID {}", id);

		final FavList favList = favListService.getFavListById(id);
		
		if (favList == null) {
			LOGGER.warn("Failed to retrieve collection with ID {}, not found", id);
			return Response.status(Status.NOT_FOUND).build();
		}
		
		LOGGER.debug("Retrieved favList {}", favList);
		
		return Response.ok(new CollectionDTO(favList, uriContext.getBaseUri())).build();
	}
}
