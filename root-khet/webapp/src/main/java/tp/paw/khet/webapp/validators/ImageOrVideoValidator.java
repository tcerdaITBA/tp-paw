package tp.paw.khet.webapp.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tp.paw.khet.webapp.form.FormProduct;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;

/**
 * Validates that at least one image or video is being uploaded.
 *
 */
public class ImageOrVideoValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return FormProduct.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		FormProduct product = (FormProduct) target;
		
		for (MultipartFileImageWrapper image: product.getImages()){
			if(image.hasFile())
				return;
		}
		
		for (VideoStringWrapper video: product.getVideos()){
			if(video.hasUrl())
				return;
		}

		errors.rejectValue("images", "imageOrVideo");

	}
	
}
