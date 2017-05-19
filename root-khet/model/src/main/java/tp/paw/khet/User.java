package tp.paw.khet;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

public class User {
	private final int userId;
	private final String name;
	private final String email;
	private final String password;
	
	public User(int userId, String name, String email, String password) {
		isTrue(userId >= 0, "User ID must be non negative: %d", userId);
		
		this.userId = userId;
		this.name = notBlank(name, "User name must have at least one non empty character");
		this.email = notBlank(email, "User email must have at least one non empty character");
		this.password = notEmpty(password, "User password must have at least one character");
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;
		
		User other = (User) obj;
		
		return userId == other.userId || email.equals(other.email);
	}
	
	@Override
	public int hashCode() {
		return userId;
	}
	
	@Override
	public String toString() {
		return name + " " + email;
	}
}
