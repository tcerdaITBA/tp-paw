package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.Comment;
import tp.paw.khet.Product;
import tp.paw.khet.User;

public interface CommentDao {
	/**
	 * Creates a {@link Comment} associated with no other comments, inserting it into the database.
	 * @param content - Content of the comment
	 * @param date - The date the comment was posted
	 * @param productId - ID of the {@link Product} this comment belongs to
	 * @param userId - ID of the {@link User} the posted this comment
	 * @return The created {@link Comment}
	 */
	public Comment createParentComment(String content, LocalDateTime date, int productId, int userId);
	
	/**
	 * Creates a {@link Comment} associated with a parent comment, inserting it into the database.
	 * @param content - Content of the comment
	 * @param date - The date the comment was posted
	 * @param parentId - ID of the {@link Comment} this comment is associated with
	 * @param productId - ID of the {@link Product} this comment belongs to
	 * @param userId - ID of the {@link User} the posted this comment
	 * @return The created {@link Comment}
	 */
	public Comment createComment(String content, LocalDateTime date, int parentId, int productId, int userId);
	
	/**
	 * Lists comments of a specific {@link Product} sorted by parent comments first, 
	 * then child comments of the first parent comment and so on.
	 * @param productId - ID of the product the comments refer to.
	 * @return {@link List} of comments associated with the {@link Product}. 
	 * 		   Could be empty if there are no comments associated with the product.
	 */
	public List<Comment> getCommentsByProductId(int productId);
}
