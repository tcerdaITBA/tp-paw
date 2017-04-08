package tp.paw.khet.webapp.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tp.paw.khet.webapp.form.FormProduct;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;

/**
 * Validates that at least one image or video is being uploaded.
 *
 */
@Component
public class ImageOrVideoValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return FormProduct.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		FormProduct formProduct = (FormProduct) target;
		
		for (MultipartFileImageWrapper image : formProduct.getImages())
			if(image.hasFile())
				return;
		
		for (VideoStringWrapper video : formProduct.getVideos())
			if(video.hasUrl())
				return;

		errors.rejectValue("images", "ImageOrVideo");
	}
	
}
