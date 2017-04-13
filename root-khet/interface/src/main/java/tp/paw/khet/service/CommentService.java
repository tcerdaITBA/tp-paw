package tp.paw.khet.service;

import java.util.List;
import tp.paw.khet.Comment;

public interface CommentService {
	
	public List<Comment> getCommentsByProductId(int id);
	public Comment createComment(String content, Integer parentId, int productId, int userId );
}
