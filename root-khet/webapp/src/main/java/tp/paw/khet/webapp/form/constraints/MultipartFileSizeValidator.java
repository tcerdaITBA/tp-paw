package tp.paw.khet.webapp.form.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

	private int min;
	private int max;

	public void initialize(FileSize constraintAnnotation) {
		min = constraintAnnotation.min();
		max = constraintAnnotation.max();
	}

	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if (value == null)
			return true;

		long size = value.getSize();
		return size >= min && size <= max;
	}

}
