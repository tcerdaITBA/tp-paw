package tp.paw.khet;

public final class CommentAndCommenter {
	private final Comment comment;
	private final User commenter;
	
	public CommentAndCommenter(Comment comment, User commenter) {
		if (comment == null)
			throw new IllegalArgumentException("Comment cannot be null");
		if (commenter == null)
			throw new IllegalArgumentException("Commenter cannot be null");
		
		this.comment = comment;
		this.commenter = commenter;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public User getCommenter() {
		return commenter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + comment.hashCode();
		result = prime * result + commenter.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CommentAndCommenter))
			return false;
		
		CommentAndCommenter other = (CommentAndCommenter) obj;
		return commenter.equals(other.commenter) && comment.equals(other.comment);
	}
	
	@Override
	public String toString() {
		return commenter.toString() + ": " + comment.toString();
	}
}
