package tp.paw.khet.persistence;

import java.time.LocalDateTime;
import java.util.List;

import tp.paw.khet.Comment;

public interface CommentDao {
	public List<Comment> getCommentsByProductId(int id);
	public Comment createComment(String content, LocalDateTime date, Integer parentId, int productId, int userId );
}
