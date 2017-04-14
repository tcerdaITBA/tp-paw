package tp.paw.khet;

import java.time.LocalDateTime;

public class Comment {
	private final int id;
	private final int  parentId;
	private final String content;
	private final LocalDateTime commentDate;
	
	public Comment(int id, String content, LocalDateTime date) {
		this(id, -1, content, date);
	}
	
	public Comment(int id, int parentId, String content, LocalDateTime date) {
		this.id = id;
		this.parentId = parentId;
		this.content = content;
		this.commentDate = date;
	}
	
	public int getId() {
		return id;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public String getContent() {
		return content;
	}
	
	public LocalDateTime getCommentDate() {
		return commentDate;
	}
	
	public boolean hasParent() {
		return this.parentId != -1;
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
