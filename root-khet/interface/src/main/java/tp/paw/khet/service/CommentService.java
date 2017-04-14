package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Comment;
import tp.paw.khet.CommentAndCommenter;
import tp.paw.khet.structures.ParentNode;

public interface CommentService {
	public List<ParentNode<CommentAndCommenter>> getCommentsByProductId(int id);
	public Comment createComment(String content, Integer parentId, int productId, int commenterId);
}
