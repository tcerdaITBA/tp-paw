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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.Comment;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.controller.auth.SecurityUserService;
import tp.paw.khet.service.CommentService;
import tp.paw.khet.service.ProductImageService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.ResourceNotFoundException;
import tp.paw.khet.webapp.form.FormComment;
import tp.paw.khet.webapp.form.FormComments;

@Controller
public class ShowProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductImageService productImageService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private SecurityUserService securityUserService;
	
	@ModelAttribute("loggedUser")
	public User loggedUser() {
		return securityUserService.getLoggedInUser();
	}
	
	@ModelAttribute("commentsForm")
	public FormComments formComments() {
		return new FormComments();
	}
	
	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
	public ModelAndView getProduct(@PathVariable final int productId) 
	throws ResourceNotFoundException {
		
		Product product = productService.getFullProductById(productId);
		
		if (product == null)
			throw new ResourceNotFoundException();
		
		ModelAndView mav = new ModelAndView("product");
		
		mav.addObject("product", product);
		mav.addObject("images", productImageService.getImagesIdByProductId(product.getId()));
		mav.addObject("videos", product.getVideos());
		mav.addObject("creator", product.getCreator());
		mav.addObject("parentcomments", product.getCommentFamilies());
		
		return mav;
	}
	
	@RequestMapping(value = "/product/{productId}/comment", method = RequestMethod.POST)
	public ModelAndView postComment (@PathVariable final int productId,
							   @ModelAttribute("loggedUser") final User loggedUser,
							   @RequestParam(value = "parentid", required = false) Optional<Integer> parentId,
							   @RequestParam(value = "index", required = false) Optional<Integer> index,
							   @Valid @ModelAttribute("commentsForm") FormComments form, 
							   BindingResult errors,
							   RedirectAttributes attr) {
		
		FormComment postedForm = index.isPresent() ? form.getChildForm(index.get()) : form.getParentForm();
		ModelAndView mav = new ModelAndView("redirect:/product/" + productId);
		
		if (errors.hasErrors()) {
			String errorForm = index.isPresent() ? index.get().toString() : "parent";
			setErrorState(productId, form, errors, attr, errorForm);
			return mav;
		}
		
		Comment comment;
		if (parentId.isPresent())
			comment = commentService.createComment(postedForm.getContent(), parentId.get(), productId, loggedUser.getUserId());
		else
			comment = commentService.createParentComment(postedForm.getContent(), productId, loggedUser.getUserId());
		
		attr.addFlashAttribute("comment", comment.getId());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/image/{imageId}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] getProductImage(@PathVariable final int productId, @PathVariable final int imageId) {
		return productImageService.getImageByIds(imageId, productId).getData();
	}
	
	@ResponseBody
	@RequestMapping(value = "/product/{productId}/logo", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public byte[] deliverLogo(@PathVariable(value = "productId") int productId) {
		return productService.getLogoByProductId(productId);
	}
	
	private void setErrorState(int productId, FormComments form, final BindingResult errors, RedirectAttributes attr, String errorForm) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.commentsForm", errors)
			.addFlashAttribute("commentsForm", form)
			.addFlashAttribute("form", errorForm);
	}
}