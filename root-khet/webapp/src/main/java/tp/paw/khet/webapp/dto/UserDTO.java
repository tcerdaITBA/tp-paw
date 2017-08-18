package tp.paw.khet.webapp.dto;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tp.paw.khet.model.User;

@XmlRootElement
public class UserDTO {
	private int id;
	private String name;
	private String email;
    private URI url;
    
    @XmlElement(name = "picture_url")
    private URI pictureURL;
    
    @XmlElement(name = "collections_url")
    private URI collectionsURL;
    
    @XmlElement(name = "voted_products_url")
    private URI votedProductsURL;
    
    @XmlElement(name = "created_products_url")
    private URI createdProductsURL;

	public UserDTO() {
	}

	public UserDTO(final User user, final URI baseUri) {
		id = user.getUserId();
		name = user.getName();
		email = user.getEmail();
		url = baseUri.resolve("users/" + id);
		pictureURL = baseUri.resolve("users/" + id + "/picture");
		collectionsURL = baseUri.resolve("users/" + id + "/collections");
		votedProductsURL = baseUri.resolve("users/" + id + "/voted_products");
		createdProductsURL = baseUri.resolve("users/" + id + "/created_products");
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

	public URI getCollectionsURL() {
		return collectionsURL;
	}

	public void setCollectionsURL(URI collectionsURL) {
		this.collectionsURL = collectionsURL;
	}

	public URI getVotedProductsURL() {
		return votedProductsURL;
	}

	public void setVotedProductsURL(URI votedProductsURL) {
		this.votedProductsURL = votedProductsURL;
	}

	public URI getCreatedProductsURL() {
		return createdProductsURL;
	}

	public void setCreatedProductsURL(URI createdProductsURL) {
		this.createdProductsURL = createdProductsURL;
	}
}
