package tp.paw.khet.webapp.form;

import javax.validation.constraints.Size;

public class FormFavList {

	@Size(min = 4, max = 64)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
