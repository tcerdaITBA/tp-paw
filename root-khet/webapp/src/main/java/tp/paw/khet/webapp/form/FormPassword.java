package tp.paw.khet.webapp.form;

import javax.validation.constraints.Size;

public class FormPassword {

	@Size(min=6, max=60)
	String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
