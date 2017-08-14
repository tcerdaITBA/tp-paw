package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.Date;
import java.util.Optional;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

// TODO: ver si FullProductDTO puede extender de este para no repetir código
// No me anduvo porque no reconoce que FullProduct tiene los campos de PlainProduct.
// Tiene que haber atributos para agregar

@XmlRootElement
public class PlainProductDTO {
	private int id;
	private String name;
	private String tagline;
	private String category;
	private URI url;
	private boolean voted;
	
	@XmlElement(name = "logo_url")
	private URI logoURL;
	
	@XmlElement(name = "creator_url")
	private URI creatorURL;
	
	@XmlElement(name = "voters_url")
	private URI votersURL;
	
	@XmlElement(name = "upload_date")
	private Date uploadDate;
	
	@XmlElement(name = "voters_count")
	private int votersCount;
	
	public PlainProductDTO() {}
	
	public PlainProductDTO(final Product product, final URI baseUri, final Optional<User> loggedUser) {
		id = product.getId();
		name = product.getName();
		tagline = product.getShortDescription();
		category = product.getCategory().getLowerName();
		uploadDate = product.getUploadDate();
		votersCount = product.getVotesCount();
		setVoted(loggedUser.filter(product::isVotedBy).isPresent());
		
		url = baseUri.resolve("products/" + id);
		creatorURL = baseUri.resolve("users/" + id);
		logoURL = baseUri.resolve("products/" + id + "/logo");
		votersURL = baseUri.resolve("products/" + id + "/voters");
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

	public boolean isVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}
}
