package tp.paw.khet.webapp.rest;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.dto.ProductListDTO;
import tp.paw.khet.webapp.dto.UserListDTO;
import tp.paw.khet.webapp.utils.PaginationLinkFactory;

@Path("search")
@Controller
@Produces(value = { MediaType.APPLICATION_JSON })
public class APISearchController {    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PaginationLinkFactory linkFactory;
    
    @Context
    private UriInfo uriContext;
    
    private static final int MIN_QUERY_LENGTH = 1;
    
    private static final int UNPROCESSABLE_ENTITY_CODE = 442;
    
    @GET
    @Path("/products")
    public Response getProducts(@DefaultValue("") @QueryParam("q") final String query,
            @DefaultValue("1") @QueryParam("page") final int page,
            @DefaultValue("" + ProductsController.DEFAULT_PAGE_SIZE) @QueryParam("per_page") final int pageSize) {
    	
        if (query.length() < MIN_QUERY_LENGTH) {
            // TODO: agregar mensaje de explicación ala Github
            return Response.status(UNPROCESSABLE_ENTITY_CODE).build();
        }
        
        final int maxPage = productService.getMaxProductsPageByKeyword(query, pageSize);

        if (page > maxPage) {
            LOGGER.warn("Index page out of bounds: {}", page);
            return Response.status(Status.NOT_FOUND).build(); 
            // NOT_FOUND u otra cosa? Github devuelve body y de contenido un objecto vacío ( [ ] ó { } )
        }
        
        final List<Product> products = productService.getPlainProductsByKeyword(query, page, pageSize);

        LOGGER.debug("Searching products. Query: {}, page: {} per_page: {}", query, page, pageSize);

        final Map<String, Link> links = linkFactory.createLinks(uriContext, page, maxPage);
        final Link[] linkArray = links.values().toArray(new Link[0]);

        LOGGER.debug("Links: {}", links);
        return Response.ok(new ProductListDTO(products, uriContext.getBaseUri())).links(linkArray).build();
    }
    
    @GET
    @Path("/users")
    public Response getUsers(@DefaultValue("") @QueryParam("q") final String query,
            @DefaultValue("1") @QueryParam("page") final int page,
            @DefaultValue("" + ProductsController.DEFAULT_PAGE_SIZE) @QueryParam("per_page") final int pageSize) {
    	
        if (query.length() < MIN_QUERY_LENGTH) {
            // TODO: agregar mensaje de explicación ala Github
            return Response.status(UNPROCESSABLE_ENTITY_CODE).build();
        }
        
        final int maxPage = userService.getMaxUserPageByKeyword(query, pageSize); // TODO: getMaxUsersByKeyword

        if (page > maxPage) {
            LOGGER.warn("Index page out of bounds: {}", page);
            return Response.status(Status.NOT_FOUND).build(); 
            // NOT_FOUND u otra cosa? Github devuelve body y de contenido un objecto vacío ( [ ] ó { } )
        }
        
        final List<User> users = userService.getUsersByKeyword(query, page, pageSize);

        LOGGER.debug("Searching products. Query: {}, page: {} per_page: {}", query, page, pageSize);

        final Map<String, Link> links = linkFactory.createLinks(uriContext, page, maxPage);
        final Link[] linkArray = links.values().toArray(new Link[0]);

        LOGGER.debug("Links: {}", links);

        return Response.ok(new UserListDTO(users, uriContext.getBaseUri())).links(linkArray).build();
    }
}