package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductImage;
import tp.paw.khet.model.User;
import tp.paw.khet.model.Video;

@XmlRootElement
public class ProductDTO {
	private int id;
	private String name;
	private String description;
	private String website;
	private String category;
	private UserDTO creator;
	private List<CommentDTO> comments;
	private URI url;
	private boolean voted;
	
	@XmlElement(name = "tagline")
	private String shortDescription;
	
	@XmlElement(name = "upload_date")
	private Date uploadDate;
	
	@XmlElement(name = "voters_count")
	private int votersCount;
	
	@XmlElement(name = "video_ids")
	private List<String> videoIds;
	
	@XmlElement(name = "image_urls")
	private List<URI> imageURLs;
		
	@XmlElement(name = "logo_url")
	private URI logoURL;
	
	@XmlElement(name = "voters_url")
	private URI votersURL;

	public ProductDTO(final Product product, final URI baseUri, final Optional<User> loggedUser) {
		id = product.getId();
		name = product.getName();
		description = product.getDescription();
		shortDescription = product.getShortDescription();
		website = product.getWebsite();
		category = product.getCategory().getLowerName();
		uploadDate = product.getUploadDate();
		votersCount = product.getVotesCount();
		creator = new UserDTO(product.getCreator(), baseUri);
		comments = CommentDTO.fromCommentFamilyList(product.getCommentFamilies(), baseUri);
		setVoted(loggedUser.filter(product::isVotedBy).isPresent());
		
		url = baseUri.resolve("products/" + id);
		logoURL = baseUri.resolve("products/" + id + "/logo");
		votersURL = baseUri.resolve("products/" + id + "/voters");
		
		videoIds = new ArrayList<>();
		for (final Video v : product.getVideos())
			videoIds.add(v.getVideoId());
		
		imageURLs = new ArrayList<>();
		for (final ProductImage pi : product.getImages())
			imageURLs.add(baseUri.resolve("products/" + id + "/images/" + pi.getProductImageId()));
		
	}

	public ProductDTO() {
	}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
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

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
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

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}

	public boolean isVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}
}
