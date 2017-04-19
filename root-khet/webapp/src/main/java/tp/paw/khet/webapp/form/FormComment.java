package tp.paw.khet.webapp.form;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class FormComment {

	@Valid
	private FormUser formUser = new FormUser();
	
	@Size(min = 1, max = 512)
	private String content;
	
	public FormUser getFormUser() {
		return formUser;
	}

	public String getUserName() {
		return formUser.getUserName();
	}

	public String getUserEmail() {
		return formUser.getUserEmail();
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setFormUser(FormUser formUser) {
		this.formUser = formUser;
	}
}
