package tp.paw.khet.webapp.form;

import java.time.LocalDate;

import tp.paw.khet.Product;

public class FormProduct {
	private int id;
	private String name;
	private String description;
	private String shortDescription;
	private LocalDate uploadDate;
	private byte[] logo;

	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public LocalDate getUploadDate() {
		return uploadDate;
	}
	
	public byte[] logo() {
		return logo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;
		
		FormProduct other = (FormProduct) obj;
		
		return id == other.id;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
