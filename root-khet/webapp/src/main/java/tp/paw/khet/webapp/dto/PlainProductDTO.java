package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.Date;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;

// TODO: ver si FullProductDTO puede extender de este para no repetir código
public class PlainProductDTO {
	private int id;
	private String name;
	private String tagline;
	private String category;
	private URI logoURL;
	private URI url;
	private Date uploadDate;
	private int votersCount;
	
	public PlainProductDTO() {};
	
	public PlainProductDTO(Product product, final URI baseUri) {
		id = product.getId();
		name = product.getName();
		tagline = product.getShortDescription();
		category = product.getCategory().getLowerName();
		logoURL = baseUri.resolve("/products/" + id + "/logo");
		uploadDate = product.getUploadDate();
		votersCount = product.getVotesCount();
		
		url = baseUri.resolve("/products/" + id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public URI getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(URI logoURL) {
		this.logoURL = logoURL;
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public int getVotersCount() {
		return votersCount;
	}

	public void setVotersCount(int votersCount) {
		this.votersCount = votersCount;
	}
	
	
	
}