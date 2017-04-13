package tp.paw.khet;

import java.time.LocalDateTime;
import java.util.Locale;

public final class Product {
	private final int id;
	private final String name;
	private final String description;
	private final String shortDescription;
	private final Category category;
	private final LocalDateTime uploadDate;

	public static ProductBuilder getBuilder() {
		return new ProductBuilder();
	}
	
	private Product(ProductBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.shortDescription = builder.shortDescription;
		this.category = builder.category;
		this.uploadDate = builder.uploadDate;
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
	
	public Category getCategory() {
		return category;
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
		private Category category = Category.OTHER;
		private LocalDateTime uploadDate = LocalDateTime.now();

		private ProductBuilder() {
		}
		
		public ProductBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public ProductBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public ProductBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public ProductBuilder shortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
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
		
		public Product build() {
			if (name == null || description == null || shortDescription == null)
				throw new IllegalStateException();
			return new Product(this);
		}
	}
}
