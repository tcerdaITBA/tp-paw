package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Comment;
import tp.paw.khet.CommentAndCommenter;
import tp.paw.khet.structures.ParentNode;

public interface CommentService {
	public List<ParentNode<CommentAndCommenter>> getCommentsByProductId(int id);
	public Comment createComment(String content, int parentId, int productId, int userId);
	public Comment createComment(String content, int productId, int userId);
}
