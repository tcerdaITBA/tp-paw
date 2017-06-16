package tp.paw.khet.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.User;
import tp.paw.khet.service.FavListService;
import tp.paw.khet.webapp.exception.FavListNotFoundException;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.form.FormFavList;

@Controller
@SessionAttributes(value={"createFavListForm"})
public class FavListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavListController.class);
	
	@Autowired
	private FavListService favListService;
	
	@RequestMapping(value = "/favlist/create", method = {RequestMethod.POST})
	public ModelAndView createFavList(@ModelAttribute("loggedUser") final User loggedUser,
							  @Valid @ModelAttribute("createFavListForm") final FormFavList favListForm,
							  final BindingResult errors, final RedirectAttributes attr) {
		
		LOGGER.debug("User with id {} accessed favList create POST", loggedUser.getUserId());
		
		final ModelAndView mav = new ModelAndView("redirect:/profile/" + loggedUser.getUserId());
		
		if (errors.hasErrors()) {
			LOGGER.warn("Failed to create favList: form has errors: {}", errors.getAllErrors());
			setErrorState(favListForm, errors, attr);
			return mav;
		}
		
		favListService.createFavList(favListForm.getName(), loggedUser.getUserId());
		attr.addFlashAttribute("createFavListForm", new FormFavList()); // clear form
		
		return mav;
	}

	private void setErrorState(FormFavList favListForm, BindingResult errors, RedirectAttributes attr) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.favListForm", errors);
		attr.addFlashAttribute("createFavListForm", favListForm);		
	}
	
	@RequestMapping(value="/favlist/{favListId}")
	public ModelAndView viewFavList(@PathVariable final int favListId) throws FavListNotFoundException{
		LOGGER.debug("Accessed user favList with ID: {}", favListId);
		
		final ModelAndView mav = new ModelAndView("favList");
		final FavList favList = favListService.getFavListById(favListId);
		
		if (favList == null) {
			LOGGER.warn("Cannot render favList: favlist ID not found: {}", favListId);
			throw new FavListNotFoundException();
		}
		
		mav.addObject("favlist", favList.getProductList());
		mav.addObject("favlistName", favList.getName());
		mav.addObject("creator", favList.getCreator());
		
		return mav;

	}
	
	@RequestMapping(value = "/favlist/delete/{favListId}", method = RequestMethod.POST)
	public ModelAndView deleteFavList(@PathVariable final int favListId, @ModelAttribute("loggedUser") final User loggedUser) throws FavListNotFoundException, ForbiddenException{
		
		LOGGER.debug("Accessed delete favlist POST for product with id: {}", favListId);
		
		final FavList favlist = favListService.getFavListById(favListId);
		
		if (favlist == null) {
			LOGGER.warn("Cannot render favList: favlist ID not found: {}", favListId);
			throw new FavListNotFoundException();
		}
		
		final User creator = favlist.getCreator();
		
		if (!creator.equals(loggedUser)) {
			LOGGER.warn("Failed to delete favlist with id {}: logged user with id {} is not favlist creator with id {}", 
					favListId, loggedUser.getUserId(), creator.getUserId());
			throw new ForbiddenException();
		}
		
		if (favListService.deleteFavList(favListId)) {
			LOGGER.info("Favlist with id {} deleted by user with id {}", favListId, loggedUser.getUserId());
		}
		
		return new ModelAndView("redirect:/profile/"+ loggedUser.getUserId());
		
	}
	
	@RequestMapping(value = "/favlist/add/{favListId}/{productId}", method = RequestMethod.POST)
	public ModelAndView addProductToFavList(@PathVariable final int favListId, @PathVariable final int productId,
			@ModelAttribute("loggedUser") final User loggedUser, @RequestHeader(value = "referer", required = false, defaultValue = "/") final String referrer)
					throws FavListNotFoundException, ForbiddenException {
				
		LOGGER.debug("Accessed add product with id {} to favlist with id {}", productId, favListId);
		
		final FavList favlist = favListService.getFavListById(favListId);
		
		if (favlist == null) {
			LOGGER.warn("Cannot render favList: favlist ID not found: {}", favListId);
			throw new FavListNotFoundException();
		}
		
		final User creator = favlist.getCreator();
		
		if(!creator.equals(loggedUser)){
			LOGGER.warn("Failed to add to favlist with id {}: logged user with id {} is not favlist creator with id {}", 
					favListId, loggedUser.getUserId(), creator.getUserId());
			throw new ForbiddenException();
		}
		
		favListService.addProductToFavList(favListId, productId);
		
		return new ModelAndView("redirect:/profile/"+ loggedUser.getUserId());
	}
}
