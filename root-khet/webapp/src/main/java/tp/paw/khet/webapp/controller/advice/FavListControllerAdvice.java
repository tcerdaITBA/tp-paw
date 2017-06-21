package tp.paw.khet.webapp.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import tp.paw.khet.webapp.form.FormFavList;

@ControllerAdvice
public class FavListControllerAdvice {

	@ModelAttribute("createFavListForm")
	public FormFavList favListForm() {
		return new FormFavList();
	}

}
