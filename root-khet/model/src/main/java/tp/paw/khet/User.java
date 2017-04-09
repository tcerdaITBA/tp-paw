package tp.paw.khet;

public class User {
	private int userId;
	private String name;
	private String mail;
	
	public User(int userId, String name, String mail) {
		this.userId = userId;
		this.name = name;
		this.mail = mail;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMail() {
		return mail;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;
		
		User other = (User) obj;
		
		return userId == other.userId;
	}
	
	@Override
	public int hashCode() {
		return userId;
	}
	
	@Override
	public String toString() {
		return name + " " + mail;
	}
}
