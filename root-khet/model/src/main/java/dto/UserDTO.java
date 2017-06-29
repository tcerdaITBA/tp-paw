package dto;

import tp.paw.khet.model.User;

public class UserDTO {
    
    private int userID;
    
    private String name;

    private String email;

    public UserDTO(User user) {
        userID = user.getUserId();
        name = user.getName();
        email = user.getEmail();
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDTO(){};
}
