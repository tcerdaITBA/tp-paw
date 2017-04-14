package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import structures.ParentNode;
import tp.paw.khet.Comment;
import tp.paw.khet.persistence.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<ParentNode<Comment>> getCommentsByProductId(int id) {
		
		List<Comment> comments = commentDao.getCommentsByProductId(id);
		
		List<ParentNode<Comment>> parents = new ArrayList<>();
		
		int parentIndex = 0;
		
		int commentIndex = 0;
		
		while (commentIndex < comments.size()) {
			
			Comment c = comments.get(commentIndex);
			
			if (!c.hasPerent()) {	
				ParentNode<Comment> node = new ParentNode<Comment>(c);
				parents.add(node);
				commentIndex++;
			}
			
			else if (c.getParentId() == parents.get(parentIndex).getParent().getId()) {
				parents.get(parentIndex).addChild(c);
				commentIndex++;
			}
			else
				parentIndex++;	
		}
				
		return parents;
	}

	@Override
	public Comment createComment(String content, Integer parentId, int productId, int userId) {
		return commentDao.createComment(content, LocalDateTime.now(), parentId, productId, userId);
	}

}
