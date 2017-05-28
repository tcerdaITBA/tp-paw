package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
	@SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
	@Column(name = "userid")
	private int userId;
	
	@Column(name = "userName", length = 64, nullable = false)
	private String name;
	
	@Column(length = 256, nullable = false, unique = true)
	private String email;
	
	@Column(length = 60, nullable = false)
	private String password;
	
	@Column(nullable = false, columnDefinition = "bytea")
	private byte[] profilePicture;
	
	// Hibernate
	User() {
	}
	
	// Dummy user constructor for testing
	User (final int userId, final String name, final String email, final String password, final byte[] profilePicture) {
		isTrue(userId >= 0, "User Id must be non negative. Current value: %d", userId);
		
		this.userId = userId;
		this.name = notBlank(name, "User name must have at least one non empty character");
		this.email = notBlank(email, "User email must have at least one non empty character");
		setPassword(password);
		setProfilePicture(profilePicture);
	}
	
	public User(final String name, final String email, final String password, final byte[] profilePicture) {		
		this(0, name, email, password, profilePicture);
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
	
	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setPassword(final String password) {
		this.password = notEmpty(password, "User password must have at least one character");		
	}
	
	public void setProfilePicture(final byte[] profilePicture) {
		isTrue(profilePicture != null && profilePicture.length > 0, "Profile picture array must be not null and not empty");
		this.profilePicture = profilePicture;		
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
