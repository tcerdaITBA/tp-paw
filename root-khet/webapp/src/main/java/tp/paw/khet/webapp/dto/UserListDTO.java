package tp.paw.khet.webapp.dto;

import java.util.List;
import java.util.LinkedList;

import tp.paw.khet.model.User;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserListDTO {
	private List<UserDTO> users;

	public UserListDTO() {
	};

	public UserListDTO(List<User> users, int votedId) {
		this.users = new LinkedList<>();

		for (User u : users)
			this.users.add(new UserDTO(u));
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
}
