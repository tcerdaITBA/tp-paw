package tp.paw.khet.webapp.dto;

import tp.paw.khet.model.User;

public class UserDTO {
    
    private int id;
    
    private String name;

    private String email;
    
    public UserDTO() {};
    
    public UserDTO(User user) {
        id = user.getUserId();
        name = user.getName();
        email = user.getEmail();
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
}
