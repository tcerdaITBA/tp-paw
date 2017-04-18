package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.Comment;
import tp.paw.khet.CommentAndCommenter;

public interface CommentDao {
	public List<CommentAndCommenter> getCommentsByProductId(int id);
	public Comment createComment(String content, LocalDateTime date, int parentId, int productId, int userId);
	public Comment createComment(String content, LocalDateTime date, int productId, int userId);
}
