package tp.paw.khet.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormUser {
	
	public final static String MAIL_PATH = "formUser.userEmail";
	
	@Size(max=30, min=4)
	@Pattern(regexp = "[A-Za-z0-9_\\s\\-.]+")
	private String userName;
	
	@NotEmpty
	@Email
	private String userEmail;

	public String getUserName() {
		return userName;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
