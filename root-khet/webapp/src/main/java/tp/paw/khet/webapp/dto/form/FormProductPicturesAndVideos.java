package tp.paw.khet.webapp.dto.form;

import java.util.List;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import tp.paw.khet.webapp.form.constraints.AtLeastOneNotEmpty;

@AtLeastOneNotEmpty(first = "pictures", second = "videos")
public class FormProductPicturesAndVideos {
	
	private List<FormDataBodyPart> pictures;
	private List<String> videos;
	
	public FormProductPicturesAndVideos() {}
	
	public FormProductPicturesAndVideos(final List<FormDataBodyPart> pictures, final List<String> videos) {
		this.setPictures(pictures);
		this.setVideos(videos);
	}

	public List<FormDataBodyPart> getPictures() {
		return pictures;
	}

	public void setPictures(List<FormDataBodyPart> pictures) {
		this.pictures = pictures;
	}

	public List<String> getVideos() {
		return videos;
	}

	public void setVideos(List<String> videos) {
		this.videos = videos;
	}
}
