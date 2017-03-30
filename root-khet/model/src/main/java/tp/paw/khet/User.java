package tp.paw.khet;

public class User {
	private String name;
	private String mail;
	
	public User(String name, String mail) {
		this.name = name;
		this.mail = mail;
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
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;
		
		User other = (User) obj;
		
		return name.equals(other.name) && mail.equals(other.mail);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() ^ mail.hashCode();
	}
}
