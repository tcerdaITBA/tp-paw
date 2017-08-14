package tp.paw.khet.webapp.dto.form;

import javax.validation.constraints.Pattern;

public class FormVideoId {

	@Pattern(regexp = "[a-zA-Z0-9_-]{11}")
	private String videoId;
	
	public FormVideoId() {}
	
	public FormVideoId(String id) {
		videoId = id;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String id) {
		videoId = id;
	}
}
