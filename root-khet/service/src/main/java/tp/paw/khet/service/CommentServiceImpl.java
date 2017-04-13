package tp.paw.khet.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Comment;
import tp.paw.khet.persistence.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public List<Comment> getCommentsByProductId(int id) {
		commentDao.getCommentsByProductId(id);
		return null;
	}

	@Override
	public Comment createComment(String content, Integer parentId, int productId, int userId) {
		return commentDao.createComment(content, LocalDateTime.now(), parentId, productId, userId);
	}

}
