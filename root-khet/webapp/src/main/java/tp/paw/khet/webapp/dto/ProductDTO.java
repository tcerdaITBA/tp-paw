package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductImage;
import tp.paw.khet.model.Video;

public class ProductDTO {
	private int id;
	private String name;
	private String description;
	private String shortDescription;
	private String website;
	private Category category;
	private Date uploadDate;
	private int votersCount;
	private UserDTO creator;
	private List<String> videoIds;
	private List<URI> imageURLs;
	private URI logoURL;
	private URI votersURL;

	public ProductDTO(final Product product, final URI baseUri) {
		id = product.getId();
		name = product.getName();
		description = product.getDescription();
		shortDescription = product.getShortDescription();
		website = product.getWebsite();
		category = product.getCategory();
		uploadDate = product.getUploadDate();
		votersCount = product.getVotesCount();
		
		logoURL = baseUri.resolve("/products/" + id + "/logo");
		
		videoIds = new ArrayList<>();
		
		for (final Video v : product.getVideos())
			videoIds.add(v.getVideoId());
		
		imageURLs = new ArrayList<>();

		for (final ProductImage pi : product.getImages())
			imageURLs.add(baseUri.resolve("/products/" + id + "/images/" + pi.getProductImageId()));
		
		setVotersURL(baseUri.resolve("/products/" + id + "/voters"));
	}

	public ProductDTO() {
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVotersCount() {
		return votersCount;
	}

	public void setVotersCount(int votersCount) {
		this.votersCount = votersCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public URI getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(URI logoURL) {
		this.logoURL = logoURL;
	}

	public UserDTO getCreator() {
		return creator;
	}

	public void setCreator(UserDTO creator) {
		this.creator = creator;
	}

	public List<String> getVideoIds() {
		return videoIds;
	}

	public void setVideoIds(List<String> videoIds) {
		this.videoIds = videoIds;
	}
	
	public List<URI> getImageURLs() {
		return imageURLs;
	}

	public void setImageURLs(List<URI> imageURLs) {
		this.imageURLs = imageURLs;
	}

	public URI getVotersURL() {
		return votersURL;
	}

	public void setVotersURL(URI votersURL) {
		this.votersURL = votersURL;
	}
}
