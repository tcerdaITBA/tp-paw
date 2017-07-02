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

	public UserDTO() {
	};

	public UserDTO(User user, final URI baseUri) {
		id = user.getUserId();
		name = user.getName();
		email = user.getEmail();
		url = baseUri.resolve("/users/" + id);
		pictureURL = baseUri.resolve("/users/" + id + "/picture");
		collectionsURL = baseUri.resolve("/users/" + id + "/collections");
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
}
