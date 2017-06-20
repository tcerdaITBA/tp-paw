package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.Validate;
import org.hibernate.annotations.SortComparator;

import tp.paw.khet.model.comparator.ProductAlphaComparator;

@Entity
@Table(name = "favLists")
public class FavList {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favLists_favListid_seq")
	@SequenceGenerator(sequenceName = "favLists_favListid_seq", name = "favLists_favListid_seq", allocationSize = 1)
	@Column(name = "favListId")
	private int id;
	
	@Column(name = "favListName", length = 64, nullable = false)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date creationDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	   joinColumns = @JoinColumn(name = "favlistid", nullable = false, foreignKey = @ForeignKey(name = "favList_id_constraint")),
	   inverseJoinColumns = @JoinColumn(name = "productid", nullable = false, foreignKey = @ForeignKey(name = "product_id_constraint")))
	@SortComparator(ProductAlphaComparator.class)
	private SortedSet<Product> productList;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User creator;
	
	public FavList(final String name, final User creator) {
		this.name = notBlank(name, "User name must have at least one non empty character");
		this.creator = notNull(creator, "FavList creator cannot be null");
		this.creationDate = new Date();
		this.productList = new TreeSet<>(new ProductAlphaComparator());
	}
	
	// Hibernate
	FavList() {
	}
	
	// Constructor for testing purposes
	FavList(final int id, final String name, final User creator) {
		this(name, creator);
		
		Validate.isTrue(id >= 0, "FavList ID must be non-negative: " + id);
		this.id = id;
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
	
	public boolean addProduct(final Product product) {
		return productList.add(notNull(product, "Product to add to FavList " + this + " cannot be null"));
	}
	
	public boolean removeProduct(final Product product) {
		return productList.remove(notNull(product, "Product to remove from FavList " + this + " cannot be null"));
	}
	
	public User getCreator() {
		return creator;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof FavList))
			return false;
		
		final FavList other = (FavList) obj;
		
		return getId() == other.getId() || (getName().equalsIgnoreCase(other.getName()) && getCreator().equals(other.getCreator()));
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
