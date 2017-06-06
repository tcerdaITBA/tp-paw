package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;
import static tp.paw.khet.model.validate.PrimitiveValidation.notEmptyByteArray;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.ArrayUtils;

@Entity
@Table(name = "products")
public class Product implements Comparable<Product> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_productid_seq")
	@SequenceGenerator(sequenceName = "products_productid_seq", name = "products_productid_seq", allocationSize = 1)
	@Column(name = "productid")	
	private int id;
	
	@Column(name = "productname", length = 64, nullable = false)
	private String name;
	
	@Column(nullable = false, columnDefinition = "text")
	private String description;
	
	@Column(length = 140, nullable = false)
	private String shortDescription;
	
	@Column(length = 256, nullable = true)
	private String website;
	
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
	@Column(nullable = false, columnDefinition = "bytea")
	private byte[] logo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="userid", nullable = false, updatable = false)
	private User creator;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productId", orphanRemoval = true)
	@OrderBy("productId ASC")
	private List<Video> videos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productId", orphanRemoval = true)
	@OrderBy("productImageId ASC")
	private List<ProductImage> images;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "votedProducts")
	@OrderBy("name ASC")
	private SortedSet<User> votingUsers;
	
	@Transient
	private List<CommentFamily> commentFamilies = Collections.emptyList();
	
	public static ProductBuilder getBuilder(final String name, final String shortDescription) {
		notBlank(name, "Product name must contain at least one non blank character");
		notBlank(shortDescription, "Product short description must contain at least one non blank character");

		return new ProductBuilder(name, shortDescription);
	}
	
	public static ProductBuilder getBuilderFromProduct(final Product product) {
		notNull(product, "Product cannot be null in order to retrieve Builder");
		
		return new ProductBuilder(product);
	}
	
	// Hibernate
	Product() {
	}
	
	private Product(ProductBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.shortDescription = builder.shortDescription;
		this.website = builder.website;
		this.category = builder.category;
		this.uploadDate = builder.uploadDate;
		this.logo = builder.logo;
		this.creator = builder.creator;
		this.commentFamilies = builder.commentFamilies;
		this.videos = builder.videos;
		this.images = builder.images;
		this.votingUsers = builder.votingUsers;
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
	
	public Date getUploadDate() {
		return uploadDate;
	}
	
	public byte[] getLogo() {
		return logo;
	}
	
	public List<CommentFamily> getCommentFamilies() {
		return Collections.unmodifiableList(commentFamilies);
	}
	
	public List<Video> getVideos() {
		return Collections.unmodifiableList(videos);
	}
	
	public List<ProductImage> getImages() {
		return Collections.unmodifiableList(images);
	}
	
	public SortedSet<User> getVotingUsers() {
		return votingUsers;
	}
	
	public int getVotesCount() {
		return getVotingUsers().size();
	}
	
	// TODO: es para hibernate que necesita que sea Comparable. No pude hacer que use un Comparator
	@Override
	public int compareTo(Product o) {
		return getName().compareTo(o.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Product))
			return false;
		
		Product other = (Product) obj;
		
		return getId() == other.getId();
	}
	
	@Override
	public int hashCode() {
		return getId();
	}
	
	@Override
	public String toString() {
		return name + " " + uploadDate + " " + shortDescription;
	}
	
	public static class ProductBuilder {
		private int id;
		private final String name;
		private final String shortDescription;
		private String description;
		private String website;
		private Category category = Category.OTHER;
		private Date uploadDate = new Date();
		private byte[] logo = ArrayUtils.EMPTY_BYTE_ARRAY;
		private User creator;
		private List<CommentFamily> commentFamilies = Collections.emptyList();
		private List<Video> videos = Collections.emptyList();
		private List<ProductImage> images = Collections.emptyList();
		private SortedSet<User> votingUsers = new TreeSet<>();  // mutable

		private ProductBuilder(String name, String shortDescription) {
			this.name = name;
			this.shortDescription = shortDescription;
		}
		
		private ProductBuilder(Product product) {
			this.id = product.getId();
			this.name = product.getName();
			this.description = product.getDescription();
			this.shortDescription = product.getShortDescription();
			this.website = product.getWebsite();
			this.category = product.getCategory();
			this.uploadDate = product.getUploadDate();
			this.logo = product.getLogo();
			this.creator = product.getCreator();
			this.commentFamilies = product.getCommentFamilies();
			this.videos = product.getVideos();
			this.images = product.getImages();
			this.votingUsers = product.getVotingUsers();
		}

		public ProductBuilder id(int id) {
			this.id = id;
			return this;
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
		
		public ProductBuilder uploadDate(Date uploadDate) {
			this.uploadDate = uploadDate;
			return this;
		}
		
		public ProductBuilder logo(byte[] logo) {
			this.logo = notEmptyByteArray(logo, "Product logo array cannot be null", "Product logo array cannot be empty");
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
		
		public ProductBuilder images(List<ProductImage> images) {
			this.images = images;
			return this;
		}
		
		public ProductBuilder votingUsers(SortedSet<User> votingUsers) {
			this.votingUsers = votingUsers;
			return this;
		}
		
		public Product build() {
			return new Product(this);
		}
	}
}
