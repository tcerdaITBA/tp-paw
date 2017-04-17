package tp.paw.khet.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FormComment {

	@Size(max=30, min=4)
	@Pattern(regexp = "[A-Za-z0-9_\\s\\-.]+")
	private String userName;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Size(max = 512)
	private String content;
	
	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getContent() {
		return content;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
