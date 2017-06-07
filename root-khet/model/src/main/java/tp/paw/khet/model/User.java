package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;
import static tp.paw.khet.model.validate.PrimitiveValidation.notEmptyByteArray;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Comparable<User> {
	
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
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "votes",
			   joinColumns = @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT)),
			   inverseJoinColumns = @JoinColumn(name = "productId", nullable = false), foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
	@OrderBy("name ASC")
	private SortedSet<Product> votedProducts = new TreeSet<>();  // mutable
	
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
	
	public SortedSet<Product> getVotedProducts() {
		return votedProducts;
	}

	public void setPassword(final String password) {
		this.password = notEmpty(password, "User password must have at least one character");		
	}
	
	public void setProfilePicture(final byte[] profilePicture) {
		this.profilePicture = notEmptyByteArray(profilePicture, "Profile picture array cannot be null", "Profile picture array cannot be empty");		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;
		
		User other = (User) obj;
		
		return getUserId() == other.getUserId() || getEmail().equals(other.getEmail());
	}
	
	@Override
	public int hashCode() {
		return getUserId();
	}
	
	@Override
	public String toString() {
		return name + " " + email;
	}

	// TODO: es para hibernate que necesita que sea Comparable. No pude hacer que use un Comparator
	@Override
	public int compareTo(User o) {
		int cmp = getName().compareTo(o.getName());
		
		if (cmp == 0)
			return Integer.compare(getUserId(), o.getUserId());
		
		return cmp;
	}
}