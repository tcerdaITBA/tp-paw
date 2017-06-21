package tp.paw.khet.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import tp.paw.khet.model.Comment;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

@Repository
public class CommentHibernateDao implements CommentDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Comment> getCommentsByProductId(final int productId) {
		final TypedQuery<Comment> query = em.createQuery("from Comment as c where c.commentedProduct.id = :productId "
				+ "ORDER BY c.parent.id NULLS FIRST, c.commentDate ASC", Comment.class);
		query.setParameter("productId", productId);

		return query.getResultList();
	}

	@Override
	public Comment createComment(final String content, final Date date, final Comment parent, final Product product, final User author) {
		final Comment comment = new Comment(parent, author, product, content, date);

		em.persist(comment);

		return comment;
	}

	@Override
	public Comment createParentComment(final String content, final Date date, final Product product, final User author) {
		final Comment comment = new Comment(author, product, content, date);

		em.persist(comment);

		return comment;
	}

	@Override
	public Comment getCommentById(int commentId) {
		return em.find(Comment.class, commentId);
	}
}
