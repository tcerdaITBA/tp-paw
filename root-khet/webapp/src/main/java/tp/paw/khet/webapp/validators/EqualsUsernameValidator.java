package tp.paw.khet.webapp.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import tp.paw.khet.User;
import tp.paw.khet.webapp.form.FormUser;

/**
 * Validates that {@link User} with the form's Email has the 
 * same Username registered as the one in the form
 */
@Component
public class EqualsUsernameValidator implements Validator {

	public static UserNamePair buildUserNamePair(String expectedUserName, String retrievedUserName) {
		return new UserNamePair(expectedUserName, retrievedUserName);
	}
	
	public boolean supports(Class<?> clazz) {
		return UserNamePair.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		UserNamePair userPair = (UserNamePair) target;
		String expectedUserName = userPair.expectedUserName;
		String retrievedUserName = userPair.retrievedUserName;
		
		String prefix = errors.getNestedPath();
		if (prefix.length() > 0)
			errors.popNestedPath();
		
		if (!expectedUserName.equals(retrievedUserName))
			errors.rejectValue(prefix + FormUser.MAIL_PATH, "EqualsUsernameValidator", new Object[]{retrievedUserName}, "EqualsUsernameValidator");
	}

	private static class UserNamePair {
		private String expectedUserName;
		private String retrievedUserName;
		
		public UserNamePair(String expectedUserName, String retrievedUserName) {
			this.expectedUserName = expectedUserName;
			this.retrievedUserName = retrievedUserName;
		}
	}
}
