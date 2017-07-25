package tp.paw.khet.webapp.rest;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.model.Category;
import tp.paw.khet.model.Comment;
import tp.paw.khet.model.OrderCriteria;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductImage;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;
import tp.paw.khet.service.CommentService;
import tp.paw.khet.service.ProductImageService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.dto.CommentDTO;
import tp.paw.khet.webapp.dto.ProductDTO;
import tp.paw.khet.webapp.dto.ProductListDTO;
import tp.paw.khet.webapp.dto.UserListDTO;
import tp.paw.khet.webapp.dto.form.FormComment;
import tp.paw.khet.webapp.dto.form.FormPicture;
import tp.paw.khet.webapp.dto.form.FormProduct;
import tp.paw.khet.webapp.dto.form.FormProductPictures;
import tp.paw.khet.webapp.dto.form.FormProductPicturesAndVideos;
import tp.paw.khet.webapp.dto.form.FormVideoId;
import tp.paw.khet.webapp.exception.DTOValidationException;
import tp.paw.khet.webapp.utils.PaginationLinkFactory;
import tp.paw.khet.webapp.validators.DTOConstraintValidator;

@Path("products")
@Controller
@Produces(value = {MediaType.APPLICATION_JSON}) 
public class ProductsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsController.class);
	
	// TODO: poner en algun lado
	public static final int MAX_PAGE_SIZE = 100;
	public static final int DEFAULT_PAGE_SIZE = 20;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private PaginationLinkFactory linkFactory;
	
	@Autowired
	private DTOConstraintValidator validator;
	
	@Autowired
	private SecurityUserService securityUserService;

	@Context
	private UriInfo uriContext;

	@GET
	@Path("/{id}")
	public Response getProductById(@PathParam("id") final int id) {
		LOGGER.debug("Accesed getProductById with ID: {}", id);
		
		final Product product = productService.getFullProductById(id);
		
		if (product == null) {
			LOGGER.warn("Product with ID: {} not found", id);
			return Response.status(Status.NOT_FOUND).build();
		} 
		else {
			return Response.ok(new ProductDTO(product, uriContext.getBaseUri())).build();
		}
	}

	@GET
	@Path("/")
	public Response getProducts(@QueryParam("category") final Category category,
			@DefaultValue("1") @QueryParam("page") int page,
			@DefaultValue("" + DEFAULT_PAGE_SIZE) @QueryParam("per_page") int pageSize,
			@DefaultValue("date") @QueryParam("sorted_by") final ProductSortCriteria sortCriteria,
			@DefaultValue("asc") @QueryParam("order") final OrderCriteria order) {

		final Optional<Category> categoryOpt = Optional.ofNullable(category);			
		
		// Ignoro valores inválidos, queda en el default.
		page = (page < 1) ? 1 : page;
		pageSize = (pageSize < 1 || pageSize > MAX_PAGE_SIZE) ? DEFAULT_PAGE_SIZE : pageSize; 

		LOGGER.debug("Accesing product list. Category: {}, page: {}, per_page: {}, sort: {}, order: {}", categoryOpt, page,
				pageSize, sortCriteria, order);
		
		final int maxPage = productService.getMaxProductPageWithSize(categoryOpt, pageSize);

		final List<Product> products = productService.getPlainProductsPaged(categoryOpt, sortCriteria, order, page, pageSize);

		final Map<String, Link> links = linkFactory.createLinks(uriContext, page, maxPage);
		final Link[] linkArray = links.values().toArray(new Link[0]);

		LOGGER.debug("Links: {}", links);
		return Response.ok(new ProductListDTO(products, uriContext.getBaseUri())).links(linkArray).build();
	}

	@GET
	@Path("{id}/voters")
	public Response getProductVoters(@PathParam("id") final int id) {
		LOGGER.debug("Accesed voters with product ID: {}", id);
		
		final Product product = productService.getFullProductById(id);		
		
		if (product == null) {
			LOGGER.warn("Product with ID: {} not found", id);
			return Response.status(Status.NOT_FOUND).build();
		} 
		else {
			final List<User> users = new LinkedList<>(product.getVotingUsers());
			return Response.ok(new UserListDTO(users, uriContext.getBaseUri())).build();
		}
	}
	
	@GET
	@Path("{id}/logo")
	@Produces({"image/png", "image/jpeg"})
	public Response getProductLogo(@PathParam("id") final int id) {
		LOGGER.debug("Accessed getProductLogo with product ID: {}", id);
		
		final byte[] logo = productService.getLogoByProductId(id);

		if (logo.length == 0) {
			LOGGER.warn("Product with ID: {} not found", id);
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(logo).build();
	}
	
	@GET
	@Path("{productId}/images/{imageId}")
	@Produces({"image/png", "image/jpeg"})
	public Response getProductImage(@PathParam("productId") final int productId, @PathParam("imageId") final int imageId) {
		LOGGER.debug("Accessed getProductImage with product ID: {} and image ID: {}", productId, imageId);
		final ProductImage image = productImageService.getImageByIds(imageId, productId);
		
		if (image == null) {
			LOGGER.warn("Image with product ID: {} and image ID: {} not found", productId, imageId);
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(image.getData()).build();
	}
	
	@POST
	@Path("{productId}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response commentProduct(@PathParam("productId") final int productId, final FormComment formComment) throws DTOValidationException {
		final Product product = productService.getPlainProductById(productId);
		
		if (product == null) {
			LOGGER.debug("Failed to commment product {}, not found", productId);
			return Response.status(Status.NOT_FOUND).build();
		}
		
		final User user = securityUserService.getLoggedInUser();
		
		validator.validate(formComment, "Failed to validate comment");
		
		final Comment comment = formComment.hasParentId() ? 
				commentService.createComment(formComment.getContent(), formComment.getParentId(), productId, user.getUserId()) : 
				commentService.createParentComment(formComment.getContent(), productId, user.getUserId());
		
		return Response.ok(new CommentDTO(comment, uriContext.getBaseUri())).build();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createProduct(@FormDataParam("product") final FormProduct formProduct, @BeanParam final FormProductPictures formPictures) 
			throws DTOValidationException {
		LOGGER.debug("Accessed createProduct");
		
		// @FormDataParam parameter is optional --> it may be null
		if (formProduct == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		performFormValidations(formProduct, formPictures);
		
		final User creator = securityUserService.getLoggedInUser();
		final Product product = productService.createProduct(formProduct.getName(), formProduct.getDescription(), formProduct.getTagline(), 
				formProduct.getWebsite(), formProduct.getAsCategory(), formPictures.getLogoBytes(), 
				creator.getUserId(), formPictures.getPicturesBytes(), Arrays.asList((formProduct.getVideoIds())));
		final URI location = uriContext.getAbsolutePathBuilder().path(String.valueOf(product.getId())).build();
		
    	return Response.created(location).entity(new ProductDTO(product, uriContext.getBaseUri())).build();
	}
	
	private void performFormValidations(final FormProduct formProduct, final FormProductPictures formPictures) throws DTOValidationException {
		validator.validate(formProduct, "Failed to validate product");
		
		for (String id : formProduct.getVideoIds())
			validator.validate(new FormVideoId(id), "Failed to validate product");
		
		validator.validate(formPictures, "Failed to validate product pictures");
		
		for (FormDataBodyPart bodyPart : formPictures.getPictures())
			validator.validate(new FormPicture(bodyPart), "Failed to validate product pictures");
		
		validator.validate(new FormProductPicturesAndVideos(formPictures.getPictures(), Arrays.asList(formProduct.getVideoIds())), "Failed to validate product");		
	}
}