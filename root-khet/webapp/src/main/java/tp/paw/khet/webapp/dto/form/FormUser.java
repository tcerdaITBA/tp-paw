package tp.paw.khet.webapp.dto.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class FormUser {
	
	@Size(max = 30, min = 4)
	@Pattern(regexp = "[\\p{L}0-9_\\s\\-.]+")
	@NotBlank
	private String name;

	@Email
	@NotBlank
	private String email;
	
	@Size(min = 6, max = 60)
	@NotBlank
	private String password;
	
	public FormUser() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
