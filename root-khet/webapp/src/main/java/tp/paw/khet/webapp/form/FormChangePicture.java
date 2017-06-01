package tp.paw.khet.webapp.form;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.internal.NotNull;

import tp.paw.khet.webapp.form.constraints.FileMediaType;
import tp.paw.khet.webapp.form.constraints.FileSize;

public class FormChangePicture {

	@FileMediaType({MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})	
	@FileSize(min = 1)
	@NotNull
	private MultipartFile profilePictureFile;

	public MultipartFile getProfilePictureFile() {
		return profilePictureFile;
	}

	public void setProfilePictureFile(MultipartFile profilePictureFile) {
		this.profilePictureFile = profilePictureFile;
	}
}
