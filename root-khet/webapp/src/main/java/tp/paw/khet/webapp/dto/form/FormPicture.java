package tp.paw.khet.webapp.dto.form;

import javax.validation.constraints.NotNull;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import tp.paw.khet.webapp.form.constraints.FileMediaType;

public class FormPicture {

	@NotNull
	@FileMediaType(value = { "image/jpeg", "image/png" })
	private FormDataBodyPart pictureBodyPart;

	public FormPicture() {}

	public FormPicture(final FormDataBodyPart pictureBodyPart) {
		this.pictureBodyPart = pictureBodyPart;
	}

	
	public FormDataBodyPart getPictureBodyPart() {
		return pictureBodyPart;
	}

	public void setPictureBodyPart(final FormDataBodyPart pictureBodyPart) {
		this.pictureBodyPart = pictureBodyPart;
	}
}
