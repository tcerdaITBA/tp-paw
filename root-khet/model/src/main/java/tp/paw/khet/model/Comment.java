package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

import java.util.Date;
import java.util.NoSuchElementException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comments")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_commentid_seq")
	@SequenceGenerator(sequenceName = "comments_commentid_seq", name = "comments_commentid_seq", allocationSize = 1)
	@Column(name = "commentid")	
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Comment parent;
	
	@Column(length = 1024, nullable = true, name = "commentcontent")
	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date commentDate;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "userid")
	private User author;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "productid")
	private Product commentedProduct;
	
	// Hibernate
	Comment() {
	}
	
	public Comment(final User author, final Product commentedProduct, final String content, final Date date) {
		this(null, author, commentedProduct, content, date, true);
	}
	
	public Comment(final Comment parent, final User author, final Product commentedProduct, final String content, final Date date) {
		this(parent, author, commentedProduct, content, date, false);
	}
	
	private Comment(final Comment parent, final User author, final Product commentedProduct, final String content, 
			final Date date, final boolean noParent) {		
		isTrue(noParent || parent != null, "Parent comment cannot be null");
		
		this.parent = parent;
		this.author = notNull(author, "Author of the comment cannot be null");
		this.commentedProduct = notNull(commentedProduct, "Commented Product cannot be null");
		this.content = notBlank(content, "Content must have at least one non blank character");
		this.commentDate = notNull(date, "Comment date cannot be null");		
	}
	
	public int getId() {
		return id;
	}
	
	public Comment getParent() {
		if (!hasParent())
			throw new NoSuchElementException("Root comment has no parent");
		return parent;
	}
	
	public String getContent() {
		return content;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public Date getCommentDate() {
		return commentDate;
	}
	
	public Product getCommentedProduct() {
		return commentedProduct;
	}
	
	// For Testing
	Comment setCommentId(int id) {
		this.id = id;
		return this;
	}
	
	public boolean hasParent() {
		return this.parent != null;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Comment))
			return false;
		
		final Comment other = (Comment) obj;
		return getId() == other.getId();
	}

	@Override
	public String toString() {
		return content;
	}
}
