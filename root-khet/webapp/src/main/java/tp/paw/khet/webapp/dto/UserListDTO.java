package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import tp.paw.khet.model.User;

public class UserListDTO {
	private List<UserDTO> users;
	private int page;
	
	//TODO: debería tener el mismo nombre que en la query que manda el cliente? SI -> cambiarlo
	private int pageSize;
	
	public UserListDTO() {
	};

	public UserListDTO(List<User> users, int votedId, int page, int pageSize, URI baseUri) {
		this.users = new LinkedList<>();

		for (User u : users)
			this.users.add(new UserDTO(u, baseUri));
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
