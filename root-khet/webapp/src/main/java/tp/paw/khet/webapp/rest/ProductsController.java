package tp.paw.khet.webapp.rest;

import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.webapp.dto.ProductDTO;
import tp.paw.khet.webapp.dto.ProductListDTO;
import tp.paw.khet.webapp.dto.UserListDTO;

@Path("products")
@Controller
public class ProductsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    //TODO: poner en algún lado
    private static final int MAX_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_SIZE = 20;
    
    @GET
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON}) 
    public Response getProductById(@PathParam("id") final int id) {
        final Product product = productService.getFullProductById(id);
        
        if (product == null) {
        	LOGGER.warn("Product with ID: {} not found", id);
            return Response.status(Status.NOT_FOUND).build();
        }
        else {
            LOGGER.debug("Accesed product with ID: {}", id);
            return Response.ok(new ProductDTO(product)).build();
        }
    }
    
    @GET
    @Path("/")
    @Produces(value = {MediaType.APPLICATION_JSON}) 
    public Response getProducts(
            @QueryParam("category") String categoryStr,
            @DefaultValue("1") @QueryParam("page") final int page,
            // TODO: No me dejar usar los toString/valueOf?? 
            @DefaultValue("" + DEFAULT_PAGE_SIZE) @QueryParam("per_page") final int pageSize,
            @DefaultValue("ALPHABETICALLY") @QueryParam("sorted_by") final ProductSortCriteria sortCriteria,
            @DefaultValue("asc") @QueryParam("order" )final String order
            ) {
        Optional<Category> category = Optional.empty();
        if (page < 1 || pageSize < 1 || pageSize > MAX_PAGE_SIZE) { 
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        final int maxPage = productService.getMaxProductPageWithSize(category, pageSize);

        if (page > maxPage) {
            LOGGER.warn("Index page out of bounds: {}", page);
            return Response.status(Status.NOT_FOUND).build(); // NOT_FOUND u otra cosa?
        }
        List<Product> products = productService.getPlainProductsPaged(category, sortCriteria, page, pageSize);        
        LOGGER.debug("Accesing product list, category: {}, page: {}, per_page: {}, sort: {}, order: {}", 
                category, page, pageSize, sortCriteria, order);        
        return Response.ok(new ProductListDTO(products, page, pageSize, category)).build();
    }
    
    @GET
    @Path("{id}/voters")
    @Produces(value = {MediaType.APPLICATION_JSON}) 
    public Response getProductVoters(@PathParam("id") final int id) {
        final Product product = productService.getFullProductById(id);
        
        if (product == null) {
            LOGGER.warn("Product with ID: {} not found", id);
            return Response.status(Status.NOT_FOUND).build();
        }
        else {
            LOGGER.debug("Accesed voters with product ID: {}", id);
            List<User> users = new LinkedList<>(product.getVotingUsers());
            return Response.ok(new UserListDTO(users, id)).build();
        }
    }
}



