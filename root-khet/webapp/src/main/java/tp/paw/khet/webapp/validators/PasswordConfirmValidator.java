package tp.paw.khet.webapp.validators;

import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import tp.paw.khet.webapp.form.FormPassword;

/*
 * Validates that password and confirm Password input are equal
 */
@Component
public class PasswordConfirmValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return FormPassword.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConf", "valid.passwordConf");
		FormPassword passwordForm = (FormPassword) obj;
		
		if (!passwordForm.getPassword().equals(passwordForm.getPasswordConf()))
			errors.rejectValue("passwordConf", "valid.passwordConfDiff");
	}

}
