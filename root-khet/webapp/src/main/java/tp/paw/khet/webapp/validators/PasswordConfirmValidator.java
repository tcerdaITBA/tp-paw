package tp.paw.khet.webapp.validators;

import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import tp.paw.khet.webapp.form.FormUser;

/*
 * Validates that password and confirm Password input are equal
 */
@Component
public class PasswordConfirmValidator implements Validator{
	
	public boolean supports(Class<?> paramClass) {
		return FormUser.class.equals(paramClass);
	}

	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConf", "valid.passwordConf");
		FormUser user = (FormUser) obj;
		if (!user.getPassword().equals(user.getPasswordConf())) {
			errors.rejectValue("passwordConf", "valid.passwordConfDiff");
		}
	}

}
