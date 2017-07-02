package tp.paw.khet.webapp.dto;

import java.net.URI;

import tp.paw.khet.model.User;

public class UserDTO {

	private int id;
	private String name;
	private String email;
    private URI url;

	public UserDTO() {
	};

	public UserDTO(User user, final URI baseUri) {
		id = user.getUserId();
		name = user.getName();
		email = user.getEmail();
		url = baseUri.resolve("/users/" + id);
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
