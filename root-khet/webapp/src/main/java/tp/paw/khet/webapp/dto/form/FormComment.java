package tp.paw.khet.webapp.dto.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.validator.constraints.NotBlank;

public class FormComment {

	@NotBlank
	@Size(min = 1, max = 512)
	private String content;
	
	@Min(1)
	@XmlElement(name = "parent_id")
	private Integer parentId;

	public FormComment() {}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean hasParentId() {
		return parentId != null;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}
