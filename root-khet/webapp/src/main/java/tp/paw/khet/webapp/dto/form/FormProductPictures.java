package tp.paw.khet.webapp.dto.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import tp.paw.khet.webapp.form.constraints.FileMediaType;

public class FormProductPictures {
	
	@NotNull
	@FileMediaType(value = { "image/jpeg", "image/png" })
	@FormDataParam("logo")
	private FormDataBodyPart logo;
	
	@Size(max = 4)
	@FormDataParam("picture")
	private List<FormDataBodyPart> pictures = Collections.emptyList();

	public FormProductPictures() {}
	
	public FormDataBodyPart getLogo() {
		return logo;
	}

	public void setLogo(FormDataBodyPart logo) {
		this.logo = logo;
	}

	public List<FormDataBodyPart> getPictures() {
		return pictures == null ? Collections.emptyList() : pictures;
	}

	public void setPictures(List<FormDataBodyPart> pictures) {
		this.pictures = pictures;
	}

	public byte[] getLogoBytes() {
		return logo.getValueAs(byte[].class);
	}

	public List<byte[]> getPicturesBytes() {
		final List<byte[]> picturesBytes = new ArrayList<>();
		getPictures().forEach((pic) -> picturesBytes.add(pic.getValueAs(byte[].class)));
		return picturesBytes;
	}
}
