package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import tp.paw.khet.model.User;

public class UserListDTO {
	private List<UserDTO> users;	
	private int count;
	
	public UserListDTO() {
	};

	public UserListDTO(final List<User> users, final URI baseUri) {
		this.users = new LinkedList<>();
		this.setCount(users.size());
		
		for (User u : users)
			this.users.add(new UserDTO(u, baseUri));
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
