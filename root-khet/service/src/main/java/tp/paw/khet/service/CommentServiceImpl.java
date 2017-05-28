package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.model.Comment;
import tp.paw.khet.model.CommentFamily;
import tp.paw.khet.persistence.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<CommentFamily> getCommentsByProductId(final int id) {
		List<Comment> comments = commentDao.getCommentsByProductId(id);
		List<CommentFamily> parents = new ArrayList<>();
		int parentIndex = 0;
		int commentIndex = 0;
		
		while (commentIndex < comments.size()) {
			Comment c = comments.get(commentIndex);
			
			if (!c.hasParent()) {	
				CommentFamily commentFamily = new CommentFamily(c);
				parents.add(commentFamily);
				commentIndex++;
			}
			else if (c.getParentId() == parents.get(parentIndex).getParentComment().getId()) {
				parents.get(parentIndex).addChildComment(c);
				commentIndex++;
			}
			else
				parentIndex++;	
		}
				
		return parents;
	}
	
	@Override
	public Comment createComment(final String content, final int parentId, final int productId, final int userId) {
		return commentDao.createComment(content, LocalDateTime.now(), parentId, productId, userId);
	}

	@Override
	public Comment createParentComment(final String content, final int productId, final int userId) {
		return commentDao.createParentComment(content, LocalDateTime.now(), productId, userId);
	}

}
