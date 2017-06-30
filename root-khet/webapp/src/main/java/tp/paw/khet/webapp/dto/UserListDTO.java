package tp.paw.khet.webapp.dto;
import java.util.List;
import java.util.LinkedList;

import tp.paw.khet.model.User;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserListDTO {
    private List<UserDTO> users;
    private int votedProductId; 

    public UserListDTO() {};
    
    public UserListDTO(List<User> users, int votedId) {
        this.users = new LinkedList<>();
        for (User u: users) {
            this.users.add(new UserDTO(u));
        }
        this.votedProductId = votedId;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public int getVotedProductId() {
        return votedProductId;
    }

    public void setVotedProductId(int votedProductId) {
        this.votedProductId = votedProductId;
    }
    
    
}
