package tp.paw.khet.webapp.form;

import javax.validation.Valid;

public class FormComments {
	@Valid
	private FormComment parentForm;

	@Valid
	private FormComment[] childForms;

	public FormComment getParentForm() {
		return parentForm;
	}

	public void setParentForm(FormComment parentForm) {
		this.parentForm = parentForm;
	}

	public FormComment[] getChildForms() {
		return childForms;
	}

	public void setChildForms(FormComment[] childForms) {
		this.childForms = childForms;
	}

	public FormComment getChildForm(int index) {
		return childForms[index];
	}
}
