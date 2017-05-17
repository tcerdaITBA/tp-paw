package tp.paw.khet.webapp.form;

import javax.validation.Valid;

public class FormChangePassword {

	private final String currentPassword;
	private String currentPasswordConf;
	
	@Valid
	private FormPassword passwordForm;
	
	public FormChangePassword(String currentPassword) {
		this.passwordForm = new FormPassword();
		this.currentPassword = currentPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}
	
	public String getCurrentPasswordConf() {
		return currentPasswordConf;
	}
	
	public void setCurrentPasswordConf(String currentPasswordConf) {
		this.currentPasswordConf = currentPasswordConf;
	}
	
	public FormPassword getPasswordForm() {
		return passwordForm;
	}
}
