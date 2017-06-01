package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.model.Comment;
import tp.paw.khet.model.CommentFamily;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface CommentService {
	
	/**
	 * Creates a {@link Comment} associated with no other comments.
	 * @param content - Content of the comment
	 * @param productId - ID of the {@link Product} this comment belongs to
	 * @param userId - ID of the {@link User} the posted this comment
	 * @return The created {@link Comment}
	 */
	public Comment createParentComment(String content, int productId, int userId);
	
	/**
	 * Creates a {@link Comment} associated with a parent comment.
	 * @param content - Content of the comment
	 * @param parentId - ID of the {@link Comment} this comment is associated with
	 * @param productId - ID of the {@link Product} this comment belongs to
	 * @param userId - ID of the {@link User} the posted this comment
	 * @return The created {@link Comment}
	 */
	public Comment createComment(String content, int parentId, int productId, int userId);
	
	/**
	 * Retrieves a {@link List} of parent comments sorted by post date and associated with it's corresponding child comments.
	 * @param productId - ID of the product the comments belong to
	 * @return List of parent comments associated with their child comments
	 */
	public List<CommentFamily> getCommentsByProductId(int productId);
}
