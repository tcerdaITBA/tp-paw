package tp.paw.khet.webapp.form.constraints;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AtLeastOneNotEmptyValidator.class);

	private String firstFieldName;
	private String secondFieldName;
	
	@Override
	public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			final Collection firstObj = (Collection) PropertyUtils.getSimpleProperty(value, firstFieldName);
			final Collection secondObj = (Collection) PropertyUtils.getSimpleProperty(value, secondFieldName);

			boolean matches = (firstObj != null && !firstObj.isEmpty()) || (secondObj != null && !secondObj.isEmpty());

			if (!matches) {
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addNode(firstFieldName).addConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addNode(secondFieldName).addConstraintViolation();
			}

			return matches;
		} catch (final Exception e) {
			LOGGER.error("Failed to read property on object {}: {}", value, e.getMessage());
		}

		return true;
	}

}
