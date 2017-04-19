package tp.paw.khet;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class Comment {
	private static final int NO_PARENT_ID = -1;
	
	private final int id;
	private final int  parentId;
	private final String content;
	private final LocalDateTime commentDate;
	private final User author;
	
	public Comment(int id, User author, String content, LocalDateTime date) {
		this(id, NO_PARENT_ID, author, content, date);
	}
	
	public Comment(int id, int parentId, User author, String content, LocalDateTime date) {
		this.id = id;
		this.parentId = parentId;
		this.author = author;
		this.content = content;
		this.commentDate = date;
	}
	
	public int getId() {
		return id;
	}
	
	public int getParentId() {
		if (!hasParent())
			throw new NoSuchElementException("Root comment has no parent");
		return parentId;
	}
	
	public String getContent() {
		return content;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public LocalDateTime getCommentDate() {
		return commentDate;
	}
	
	public boolean hasParent() {
		return this.parentId != NO_PARENT_ID;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Comment))
			return false;
		
		Comment other = (Comment) obj;
		return getId() == other.getId();
	}

	@Override
	public String toString() {
		return content;
	}
}
