package tp.paw.khet.webapp.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tp.paw.khet.webapp.form.FormChangePassword;

@Component
public class PasswordChangeValidator implements Validator {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return FormChangePassword.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FormChangePassword form = (FormChangePassword) target;
		
		if (!passwordEncoder.matches(form.getCurrentPasswordConf(), form.getCurrentPassword()))
			errors.rejectValue("currentPasswordConf", "CurrentPasswordMatch");
	}

}
