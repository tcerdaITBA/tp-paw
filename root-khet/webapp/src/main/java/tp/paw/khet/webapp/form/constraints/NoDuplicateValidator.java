package tp.paw.khet.webapp.form.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoDuplicateValidator implements ConstraintValidator<NoDuplicates, Object[]> {

	public void initialize(NoDuplicates constraintAnnotation) {
	}

	public boolean isValid(Object[] value, ConstraintValidatorContext context) {
		if (value == null)
			return true;

		for (int i = 0; i < value.length; i++) {
			if (value[i] != null) {
				for (int j = i + 1; j < value.length; j++)
					if (value[j] != null && value[i].equals(value[j]))
						return false;
			}
		}

		return true;
	}

}
