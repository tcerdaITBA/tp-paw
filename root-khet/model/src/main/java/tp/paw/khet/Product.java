package tp.paw.khet;

import java.time.LocalDateTime;
import java.util.Locale;

public final class Product {
	private final int id;
	private final String name;
	private final String description;
	private final String shortDescription;
	private final String website;
	private final Category category;
	private final LocalDateTime uploadDate;
	private final User creator;

	public static ProductBuilder getBuilder(int id, String name, String shortDescription) {
		if (id < 0 || name == null || name.length() == 0 || shortDescription == null || shortDescription.length() == 0)
			throw new IllegalArgumentException();
		return new ProductBuilder(id, name, shortDescription);
	}
	
	private Product(ProductBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.shortDescription = builder.shortDescription;
		this.website = builder.website;
		this.category = builder.category;
		this.uploadDate = builder.uploadDate;
		this.creator = builder.creator;
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
	
	public String getWebsite() {
	    return website;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public User getCreator() {
		return creator;
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
	
	@Override
	public String toString() {
		return name + " " + uploadDate + " " + shortDescription;
	}
	
	public static class ProductBuilder {
		private int id = -1;
		private String name;
		private String description;
		private String shortDescription;
		private String website;
		private Category category = Category.OTHER;
		private LocalDateTime uploadDate = LocalDateTime.now();
		private User creator;

		private ProductBuilder(int id, String name, String shortDescription) {
			this.id = id;
			this.name = name;
			this.shortDescription = shortDescription;
		}
		
		public ProductBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public ProductBuilder website(String link) {
		    this.website = link;
		    return this;
		}
		
		public ProductBuilder category(Category category) {
			this.category = category;
			return this;
		}
		
		public ProductBuilder category(String category) {
			this.category = Category.valueOf(category.toUpperCase(Locale.ENGLISH).trim());
			return this;
		}
		
		public ProductBuilder uploadDate(LocalDateTime uploadDate) {
			this.uploadDate = uploadDate;
			return this;
		}
		
		public ProductBuilder creator(User creator) {
			this.creator = creator;
			return this;
		}
		
		public Product build() {
			return new Product(this);
		}
	}
}
