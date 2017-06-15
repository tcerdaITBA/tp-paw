package tp.paw.khet.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.model.User;
import tp.paw.khet.service.FavListService;
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
		
		return mav;
	}

	private void setErrorState(FormFavList favListForm, BindingResult errors, RedirectAttributes attr) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.favListForm", errors);
		attr.addFlashAttribute("createFavListForm", favListForm);		
	}
}
