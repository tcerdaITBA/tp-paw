package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Comment;
import tp.paw.khet.CommentAndCommenter;
import tp.paw.khet.persistence.CommentDao;
import tp.paw.khet.structures.ParentNode;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<ParentNode<CommentAndCommenter>> getCommentsByProductId(int id) {
		List<CommentAndCommenter> comments = commentDao.getCommentsByProductId(id);
		List<ParentNode<CommentAndCommenter>> parents = new ArrayList<>();
		int parentIndex = 0;
		int commentIndex = 0;
		
		while (commentIndex < comments.size()) {
			CommentAndCommenter cc = comments.get(commentIndex);
			Comment c = cc.getComment();
			
			if (!c.hasParent()) {	
				ParentNode<CommentAndCommenter> node = new ParentNode<CommentAndCommenter>(cc);
				parents.add(node);
				commentIndex++;
			}
			else if (c.getParentId() == parents.get(parentIndex).getParent().getComment().getId()) {
				parents.get(parentIndex).addChild(cc);
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
