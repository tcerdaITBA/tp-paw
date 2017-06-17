package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

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
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "favLists"
//	   ,uniqueConstraints= @UniqueConstraint(columnNames={"favListName", "creator_userId"})  TODO: column am.amcanorder does no exist
)
public class FavList implements Comparable<FavList> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favLists_favListid_seq")
	@SequenceGenerator(sequenceName = "favLists_favListid_seq", name = "favLists_favListid_seq", allocationSize = 1)
	@Column(name = "favListId")
	private int id;
	
	@Column(name = "favListName", length = 64, nullable = false)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	   joinColumns = @JoinColumn(name = "favListId", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "favList_id_constraint")),
	   inverseJoinColumns = @JoinColumn(name = "productId", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_id_constraint")))
	@OrderBy("name ASC")
	private SortedSet<Product> productList;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User creator;
	
	public FavList(final String name, final User creator) {
		this.name = notBlank(name, "User name must have at least one non empty character");
		this.creator = notNull(creator, "FavList creator cannot be null");
		this.creationDate = new Date();
		this.productList = new TreeSet<>();
	}
	
	// Hibernate
	FavList() {
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public SortedSet<Product> getProductList() {
		return Collections.unmodifiableSortedSet(productList);
	}
	
	public void addProduct(final Product product) {
		productList.add(product);
	}
	
	public boolean removeProduct(final Product product) {
		return productList.remove(product);
	}
	
	public User getCreator() {
		return creator;
	}

	@Override
	public int compareTo(final FavList o) {
		return getCreationDate().compareTo(o.getCreationDate());
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof FavList))
			return false;
		
		final FavList other = (FavList) obj;
		
		return getId() == other.getId();
	}
	
	@Override
	public int hashCode() {
		return getId();
	}
	
	@Override
	public String toString() {
		final StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append(getName()).append(":\n");
		
		for (final Product p : getProductList())
			strBuilder.append(p).append("\n");
		
		return strBuilder.toString();
	}
}
