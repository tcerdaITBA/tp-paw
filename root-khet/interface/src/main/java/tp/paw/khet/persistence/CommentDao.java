package tp.paw.khet.persistence;

import java.util.Date;
import java.util.List;

import tp.paw.khet.model.Comment;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface CommentDao {
	/**
	 * Creates a {@link Comment} associated with no other comments, inserting it into the database.
	 * @param content - Content of the comment
	 * @param date - The date the comment was posted
	 * @param product - {@link Product} this comment belongs to
	 * @param user - {@link User} that posted this comment
	 * @return The created {@link Comment}
	 */
	public Comment createParentComment(String content, Date date, Product product, User author);
	
	/**
	 * Creates a {@link Comment} associated with a parent comment, inserting it into the database.
	 * @param content - Content of the comment
	 * @param date - The date the comment was posted
	 * @param parent - {@link Comment} this comment is associated with
	 * @param product - {@link Product} this comment belongs to
	 * @param author - {@link User} that posted this comment
	 * @return The created {@link Comment}
	 */
	public Comment createComment(String content, Date date, Comment parent, Product product, User author);
	
	/**
	 * Lists comments of a specific {@link Product} sorted by parent comments first, 
	 * then child comments of the first parent comment and so on.
	 * @param productId - ID of the product the comments refer to.
	 * @return {@link List} of comments associated with the {@link Product}. 
	 * 		   Could be empty if there are no comments associated with the product.
	 */
	public List<Comment> getCommentsByProductId(int productId);

	/**
	 * Retrieves a {@link Comment} given it's ID.
	 * @param commentId - ID of the comment to retrieve
	 * @return The comment with the corresponding ID or null if it doesn't exist
	 */
	public Comment getCommentById(int commentId);
}
