package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;
import static tp.paw.khet.model.validate.PrimitiveValidation.notEmptyByteArray;

import java.util.Collections;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SortComparator;

import tp.paw.khet.model.comparator.FavListDateComparator;
import tp.paw.khet.model.comparator.ProductAlphaComparator;

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
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "votes",
			   joinColumns = @JoinColumn(name = "userId", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "user_id_contraint")),
			   inverseJoinColumns = @JoinColumn(name = "productId", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_id_contraint")))
	@SortComparator(ProductAlphaComparator.class)
	private SortedSet<Product> votedProducts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creator", orphanRemoval = true)
	@SortComparator(FavListDateComparator.class)
	private SortedSet<FavList> favLists;
	
	// Hibernate
	User() {
	}
	
	// Dummy user constructor for testing
	User (final int userId, final String name, final String email, final String password, final byte[] profilePicture) {
		isTrue(userId >= 0, "User Id must be non negative. Current value: %d", userId);
		
		this.userId = userId;
		this.name = notBlank(name, "User name must have at least one non empty character");
		this.email = notBlank(email, "User email must have at least one non empty character");
		this.votedProducts = new TreeSet<>(new ProductAlphaComparator());
		this.favLists = new TreeSet<>(new FavListDateComparator());
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
		return Collections.unmodifiableSortedSet(votedProducts);
	}
	
	public void voteProduct(final Product product) {
		votedProducts.add(product);
	}
	
	public void unvoteProduct(final Product product) {
		votedProducts.remove(product);
	}

	public void setPassword(final String password) {
		this.password = notEmpty(password, "User password must have at least one character");		
	}
	
	public void setProfilePicture(final byte[] profilePicture) {
		this.profilePicture = notEmptyByteArray(profilePicture, "Profile picture array cannot be null", "Profile picture array cannot be empty");		
	}
	
	public SortedSet<FavList> getFavLists() {
		return Collections.unmodifiableSortedSet(favLists);
	}
	
	public FavList createFavList(final String name) {
		final FavList favList = new FavList(name, this);
		favLists.add(favList);
		return favList;
	}
	
	public void deleteFavList(final FavList favList) {
		favLists.remove(favList);
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
}
