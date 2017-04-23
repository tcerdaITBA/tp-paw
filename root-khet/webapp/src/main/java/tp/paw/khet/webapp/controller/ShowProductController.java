package tp.paw.khet.webapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.service.CommentService;
import tp.paw.khet.service.ProductImageService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.service.UserService;
import tp.paw.khet.service.VideoService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.form.FormComment;
import tp.paw.khet.webapp.form.FormComments;
import tp.paw.khet.webapp.validators.EqualsUsernameValidator;

@Controller
public class ShowProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private EqualsUsernameValidator equalsUsernameValidator;
	
	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
	public ModelAndView getProduct(@PathVariable final int productId, @ModelAttribute("commentsForm") FormComments form) 
	throws ResourceNotFoundException {
					
		Product product = productService.getProductById(productId);
		
		if (product == null)
			throw new ResourceNotFoundException();
		
		ModelAndView mav = new ModelAndView("product");
		
		mav.addObject("product", product);
		mav.addObject("images", productImageService.getImagesIdByProductId(product.getId()));
		mav.addObject("videos",videoService.getVideosByProductId(product.getId()));
		mav.addObject("user", productService.getCreatorByProductId(product.getId()));
		mav.addObject("parentcomments", commentService.getCommentsByProductId(product.getId()));

		return mav;
	}
	
	@RequestMapping(value = "/product/{productId}/comment", method = RequestMethod.POST)
	public ModelAndView postComment (@PathVariable final int productId,
							   @RequestParam(value = "parentid", required = false) Optional<Integer> parentId,
							   @RequestParam(value = "index", required = false) Optional<Integer> index,
							   @Valid @ModelAttribute("commentsForm") FormComments form, 
							   BindingResult errors) {
		
		FormComment postedForm = index.isPresent() ? form.getChildForm(index.get()) : form.getParentForm();
		
		if (errors.hasErrors())
			return getProduct(productId, form);
		
		User user = userService.createUserOrRetrieveIfExists(postedForm.getUserName(), postedForm.getUserEmail());
		
		errors.pushNestedPath(index.isPresent() ? "childForms[" + index.get() + "]" : "parentForm");
		
		equalsUsernameValidator.validate(EqualsUsernameValidator.buildUserNamePair(postedForm.getUserName(), user.getName()), errors);
		
		if (errors.hasErrors())
			return getProduct(productId, form);
		
		if (parentId.isPresent())
			commentService.createComment(postedForm.getContent(), parentId.get(), productId, user.getUserId());
		else
			commentService.createParentComment(postedForm.getContent(), productId, user.getUserId());
		
		return new ModelAndView("redirect:/product/" + productId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/image/{imageId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] getProductImage(@PathVariable final int productId, @PathVariable final int imageId) {
		return productImageService.getImageByIds(imageId, productId).getData();
	}	
}
