package tp.paw.khet;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import tp.paw.khet.interfaces.PlainProduct;

public final class Product implements PlainProduct {
	private final int id;
	private final String name;
	private final String description;
	private final String shortDescription;
	private final String website;
	private final Category category;
	private final LocalDateTime uploadDate;
	private final User creator;
	private final List<CommentFamily> commentFamilies;
	private final List<Video> videos;

	public static ProductBuilder getBuilder(int id, String name, String shortDescription) {
		isTrue(id >= 0, "Product ID must be non negative: %d", id);
		notBlank(name, "Product name must contain at least one non blank character");
		notBlank(shortDescription, "Product short description must contain at least one non blank character");

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
		this.commentFamilies = builder.commentFamilies;
		this.videos = builder.videos;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getWebsite() {
	    return website;
	}
	
	@Override
	public Category getCategory() {
		return category;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public LocalDateTime getUploadDate() {
		return uploadDate;
	}
	
	public List<CommentFamily> getCommentFamilies() {
		return Collections.unmodifiableList(commentFamilies);
	}
	
	public List<Video> getVideos() {
		return Collections.unmodifiableList(videos);
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
		private final int id;
		private final String name;
		private final String shortDescription;
		private String description;
		private String website;
		private Category category = Category.OTHER;
		private LocalDateTime uploadDate = LocalDateTime.now();
		private User creator;
		private List<CommentFamily> commentFamilies = Collections.emptyList();
		private List<Video> videos = Collections.emptyList();

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

		public ProductBuilder commentFamilies(List<CommentFamily> commentFamilies) {
			this.commentFamilies = commentFamilies;
			return this;
		}
		
		public ProductBuilder videos(List<Video> videos) {
			this.videos = videos;
			return this;
		}
		
		public Product build() {
			return new Product(this);
		}
	}
}
