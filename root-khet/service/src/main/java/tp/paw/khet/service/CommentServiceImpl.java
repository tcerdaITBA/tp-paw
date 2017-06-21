package tp.paw.khet.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.model.Comment;
import tp.paw.khet.model.CommentFamily;
import tp.paw.khet.persistence.CommentDao;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

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
			} else if (c.getParent().equals(parents.get(parentIndex).getParentComment())) {
				parents.get(parentIndex).addChildComment(c);
				commentIndex++;
			} else
				parentIndex++;
		}

		return parents;
	}

	@Transactional
	@Override
	public Comment createComment(final String content, final int parentId, final int productId, final int userId) {
		return commentDao.createComment(content, new Date(), commentDao.getCommentById(parentId),
				productService.getPlainProductById(productId), userService.getUserById(userId));
	}

	@Transactional
	@Override
	public Comment createParentComment(final String content, final int productId, final int userId) {
		return commentDao.createParentComment(content, new Date(), productService.getPlainProductById(productId), userService.getUserById(userId));
	}

}
