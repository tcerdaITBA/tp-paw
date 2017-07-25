package tp.paw.khet.webapp.dto.form;

import javax.validation.constraints.Pattern;

public class FormVideoId {

	@Pattern(regexp = "[a-zA-Z0-9_-]{11}")
	private String video_id;
	
	public FormVideoId(String id) {
		video_id = id;
	}

	public String getVideo_id() {
		return video_id;
	}

	public void setVideo_id(String id) {
		video_id = id;
	}
}
