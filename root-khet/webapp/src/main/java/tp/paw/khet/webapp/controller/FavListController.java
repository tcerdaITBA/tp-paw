package tp.paw.khet.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.User;
import tp.paw.khet.service.FavListService;
import tp.paw.khet.webapp.exception.FavListNotFoundException;
import tp.paw.khet.webapp.form.FormChangePassword;
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
}
