package tp.paw.khet.webapp.form;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class FormChangePassword {

	@Size(min=6, max=60)
	private String previousPassword;
	
	@Valid
	private FormPassword passwordForm = new FormPassword();
	
	public String getPreviousPassword() {
		return previousPassword;
	}
	
	public void setPreviousPassword(String previousPassword) {
		this.previousPassword = previousPassword;
	}
	
	public FormPassword getPasswordForm() {
		return passwordForm;
	}
}
