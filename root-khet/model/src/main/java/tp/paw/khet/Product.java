package tp.paw.khet;

import java.time.LocalDateTime;

public class Product {
	private int id;
	private String name;
	private String description;
	private String shortDescription;
	private LocalDateTime uploadDate;
	
	public Product(int id, String name, String description, String shortDescription, LocalDateTime uploadDate) {
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
	
	public LocalDateTime getUploadDate() {
		return uploadDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Product))
			return false;
		
		Product other = (Product) obj;
		
		return id == other.id;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
