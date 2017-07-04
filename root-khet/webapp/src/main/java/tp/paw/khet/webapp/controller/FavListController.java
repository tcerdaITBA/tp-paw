package tp.paw.khet.webapp.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.service.FavListService;
import tp.paw.khet.service.ProductService;
import tp.paw.khet.webapp.exception.FavListNotFoundException;
import tp.paw.khet.webapp.exception.ForbiddenException;
import tp.paw.khet.webapp.exception.ProductNotFoundException;
import tp.paw.khet.webapp.form.FormFavList;

public class FavListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FavListController.class);

	@Autowired
	private FavListService favListService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/favlist/create", method = { RequestMethod.POST })
	public ModelAndView createFavList(@ModelAttribute("loggedUser") final User loggedUser,
			@RequestParam(name = "productId", required = false) final Optional<Integer> productId,
			@RequestParam(name = "publishedTab", required = false, defaultValue = "false") final boolean publishedTab,
			@Valid @ModelAttribute("createFavListForm") final FormFavList favListForm, final BindingResult errors,
			@RequestHeader(value = "referer", required = false, defaultValue = "/") final String referrer,
			final RedirectAttributes attr) throws ProductNotFoundException {

		LOGGER.debug("User with id {} accessed favList create POST", loggedUser.getUserId());

		final ModelAndView mav = new ModelAndView("redirect:" + referrer);

		if (errors.hasErrors()) {
			LOGGER.warn("Failed to create favList: form has errors: {}", errors.getAllErrors());
			setErrorState(favListForm, errors, attr, productId);
			return mav;
		}

		final FavList favList;

		try {
			favList = favListService.createFavList(favListForm.getName(), loggedUser.getUserId());
		} catch (DuplicateFavListException e) {
			LOGGER.warn("Failed to create favList: duplicate favList {}", e.getMessage());
			errors.rejectValue("name", "DuplicateFavList", new Object[] { favListForm.getName() }, "DuplicateFavList");
			setErrorState(favListForm, errors, attr, productId);
			return mav;
		}

		attr.addFlashAttribute("createFavListForm", new FormFavList()); // clear
																		// form
		attr.addFlashAttribute("favListCreated", favList.getName());
		attr.addFlashAttribute("publishedTab", publishedTab);

		if (productId.isPresent())
			addProductToFavList(productId.get(), favList, attr);

		return mav;
	}

	@RequestMapping(value = "/favlist/delete/{favListId}", method = RequestMethod.POST)
	public ModelAndView deleteFavList(@PathVariable final int favListId,
			@ModelAttribute("loggedUser") final User loggedUser, final RedirectAttributes attr)
			throws FavListNotFoundException, ForbiddenException {

		LOGGER.debug("Accessed delete favlist POST for product with id: {}", favListId);

		final FavList favList = retrieveValidFavList(favListId);

		assertValidCreator(favList, loggedUser);

		LOGGER.info("Favlist with id {} deleted by user with id {}", favListId, loggedUser.getUserId());

		favListService.deleteFavList(favListId);

		attr.addFlashAttribute("favListDeleted", favList.getName());

		return new ModelAndView("redirect:/profile/" + loggedUser.getUserId());
	}

	@RequestMapping(value = "/favlist/add/{favListId}/{productId}", method = RequestMethod.POST)
	public ModelAndView addProductToFavList(@PathVariable final int favListId, @PathVariable final int productId,
			@ModelAttribute("loggedUser") final User loggedUser,
			@RequestParam(name = "publishedTab", required = false, defaultValue = "false") final boolean publishedTab,
			@RequestHeader(value = "referer", required = false, defaultValue = "/") final String referrer,
			final RedirectAttributes attr)
			throws FavListNotFoundException, ForbiddenException, ProductNotFoundException {

		LOGGER.debug("Accessed add product with id {} to favlist with id {}", productId, favListId);

		final FavList favList = retrieveValidFavList(favListId);

		assertValidCreator(favList, loggedUser);

		addProductToFavList(productId, favList, attr);

		attr.addFlashAttribute("publishedTab", publishedTab);

		return new ModelAndView("redirect:" + referrer);
	}

	@RequestMapping(value = "/favlist/delete/{favListId}/{productId}", method = RequestMethod.POST)
	public ModelAndView removeProductFromFavList(@PathVariable final int favListId, @PathVariable final int productId,
			@ModelAttribute("loggedUser") final User loggedUser,
			@RequestHeader(value = "referer", required = false, defaultValue = "/") final String referrer,
			final RedirectAttributes attr)
			throws FavListNotFoundException, ForbiddenException, ProductNotFoundException {

		LOGGER.debug("Accessed remove product with id {} to favlist with id {}", productId, favListId);

		final FavList favList = retrieveValidFavList(favListId);

		assertValidCreator(favList, loggedUser);

		favListService.removeProductFromFavList(favListId, productId);

		attr.addFlashAttribute("productRemovedFromFavList", retrieveValidProduct(productId).getName());
		attr.addFlashAttribute("favListRemoved", favList.getName());
		attr.addFlashAttribute("favListRemovedId", favList.getId());

		return new ModelAndView("redirect:" + referrer);
	}

	private void setErrorState(final FormFavList favListForm, final BindingResult errors, final RedirectAttributes attr,
			final Optional<Integer> productId) {
		attr.addFlashAttribute("org.springframework.validation.BindingResult.createFavListForm", errors);
		attr.addFlashAttribute("createFavListForm", favListForm);
		attr.addFlashAttribute("favListError", true);

		if (productId.isPresent())
			attr.addFlashAttribute("favListErrorProductId", productId.get());
	}

	private void addProductToFavList(final int productId, final FavList favList, final RedirectAttributes attr)
			throws ProductNotFoundException {
		final Product product = retrieveValidProduct(productId);

		favListService.addProductToFavList(favList.getId(), productId);

		attr.addFlashAttribute("productAddedToFavList", product.getName());
		attr.addFlashAttribute("favListAdded", favList.getName());
	}

	private Product retrieveValidProduct(final int productId) throws ProductNotFoundException {
		final Product product = productService.getPlainProductById(productId);

		if (product == null)
			throw new ProductNotFoundException();

		return product;
	}

	private void assertValidCreator(final FavList favList, final User loggedUser) throws ForbiddenException {
		final User creator = favList.getCreator();

		if (!creator.equals(loggedUser)) {
			LOGGER.warn("Failed to add to favlist with id {}: logged user with id {} is not favlist creator with id {}",
					favList.getId(), loggedUser.getUserId(), creator.getUserId());
			throw new ForbiddenException();
		}
	}

	private FavList retrieveValidFavList(final int favListId) throws FavListNotFoundException {
		final FavList favList = favListService.getFavListByIdWithCreator(favListId);

		if (favList == null) {
			LOGGER.warn("Cannot render favList: favlist ID not found: {}", favListId);
			throw new FavListNotFoundException();
		}

		return favList;
	}
}
