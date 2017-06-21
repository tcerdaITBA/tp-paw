package tp.paw.khet.webapp.form;

import javax.validation.Valid;

public class FormChangePassword {

	private String currentPassword;
	private String currentPasswordConf;

	@Valid
	private FormPassword passwordForm;

	public FormChangePassword() {
		this.passwordForm = new FormPassword();
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
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
