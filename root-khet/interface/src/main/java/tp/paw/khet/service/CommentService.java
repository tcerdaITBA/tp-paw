package tp.paw.khet.service;

import java.util.List;

import structures.ParentNode;
import tp.paw.khet.Comment;

public interface CommentService {
	
	public List<ParentNode<Comment>> getCommentsByProductId(int id);
	public Comment createComment(String content, Integer parentId, int productId, int userId, String userName, String email);
}
