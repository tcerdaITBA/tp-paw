package tp.paw.khet.webapp.form;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

public class FormComment {
	
	@Size(min = 1, max = 512)
	private String content;

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = StringUtils.strip(content);
	}
}
