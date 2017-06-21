package tp.paw.khet.webapp.form;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import tp.paw.khet.webapp.form.constraints.FileMediaType;
import tp.paw.khet.webapp.form.constraints.FileSize;

public class FormUser {

	@Size(max = 30, min = 4)
	@Pattern(regexp = "[\\p{L}0-9_\\s\\-.]+")
	private String name;

	@Email
	@NotBlank
	private String email;

	@Valid
	private FormPassword passwordForm = new FormPassword();

	@FileMediaType({ MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
	@FileSize(min = 1)
	private MultipartFile profilePicture;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.strip(name);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = StringUtils.strip(email);
	}

	public FormPassword getPasswordForm() {
		return passwordForm;
	}

	public MultipartFile getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(MultipartFile profilePicture) {
		this.profilePicture = profilePicture;
	}

}
