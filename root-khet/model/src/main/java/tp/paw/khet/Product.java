package tp.paw.khet;

import java.time.LocalDate;

public class Product {
	private int id;
	private String name;
	private String description;
	private String shortDescription;
	private LocalDate uploadDate;
	
	public Product(int id, String name, String description, String shortDescription, LocalDate uploadDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.shortDescription = shortDescription;
		this.uploadDate = uploadDate;
	}
	
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;
		
		Product other = (Product) obj;
		
		return id == other.id;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
