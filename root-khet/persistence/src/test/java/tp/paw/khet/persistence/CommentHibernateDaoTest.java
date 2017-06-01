package tp.paw.khet.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static tp.paw.khet.model.CommentTestUtils.assertEqualsComments;
import static tp.paw.khet.model.CommentTestUtils.dummyComment;
import static tp.paw.khet.model.CommentTestUtils.dummyCommentList;
import static tp.paw.khet.model.CommentTestUtils.dummyParentComment;
import static tp.paw.khet.model.CommentTestUtils.dummyParentCommentList;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;
import static tp.paw.khet.model.UserTestUtils.profilePictureFromUser;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.Comment;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
public class CommentHibernateDaoTest {

	@Autowired
	private CommentHibernateDao commentDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
				
		insertDummyUser();
		insertDummyProduct();
	}

	@Test
	public void createParentCommentTest() {
		Comment expected = dummyParentComment(1, 1, 1);
		Comment actual = insertComment(expected);
		
		assertEqualsComments(expected, actual);
		assertFalse(actual.hasParent());
		assertEqualsComments(actual, commentDao.getCommentById(actual.getId()));
	}
	
	@Test
	public void createChildCommentTest() {
		Comment dummyParent = dummyParentComment(1, 1, 1);
		insertComment(dummyParent).getId();
		Comment expected = dummyComment(2, dummyParent, 1, 1);
		Comment actual = insertComment(expected);

		assertEqualsComments(expected, actual);
		assertTrue(actual.hasParent());
		assertEqualsComments(dummyParent, actual.getParent());
		assertEqualsComments(actual, commentDao.getCommentById(actual.getId()));
	}
	
	@Test
	public void getCommentsByProductIdTest() {
		List<Comment> parentCommentList = dummyParentCommentList(7, 1, 1, 1);
		insertCommentList(parentCommentList, 1);
		
		for (int i = 0; i < 7; i++)
			insertCommentList(dummyCommentList(5, 7 + i * 5, parentCommentList.get(i), 1, 1), 1);
		
		List<Comment> actual = commentDao.getCommentsByProductId(1);
		List<Comment> comments = new ArrayList<>(actual.size());
		for (Comment comment : actual)
			comments.add(comment);
		
		assertCommentsOrder(comments);
	}
	
	private void insertCommentList(List<Comment> comments, int productId) {
		for (Comment comment : comments)
			insertComment(comment);
	}

	// Asserts that a block of parent comments come first, then a block of child comments with parentId of the first parent comment and so on
	private void assertCommentsOrder(List<Comment> comments) {
		assertFalse(comments.get(0).hasParent());
		
		for (int i = 0, j = childrenPointer(comments); j < comments.size(); j++) {
			Comment parent = comments.get(i);
			Comment child = comments.get(j);
			
			assertFalse(parent.hasParent());
			
			if (child.getParent().getId() > parent.getId()) {
				i++;
			}
			else {
				assertEquals(parent.getId(), child.getParent().getId());
				if (j < comments.size() - 1 && child.getParent().getId() == comments.get(j+1).getParent().getId())
					assertTrue(child.getCommentDate().compareTo(comments.get(j+1).getCommentDate()) < 0); // Oldest comments first
			}
		}
	}
	
	private int childrenPointer(List<Comment> comments) {
		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).hasParent())
				return i;
			
			if (i < comments.size()-1 && comments.get(i+1).hasParent())
				assertTrue(comments.get(i).getCommentDate().compareTo(comments.get(i+1).getCommentDate()) < 0); // Oldest comments first
		}
		
		return comments.size();
	}
	
	private Comment insertComment(Comment comment) {
		if (comment.hasParent())
			return commentDao.createComment(comment.getContent(), comment.getCommentDate(), comment.getParent(), comment.getCommentedProduct(), comment.getAuthor());
		return commentDao.createParentComment(comment.getContent(), comment.getCommentDate(), comment.getCommentedProduct(), comment.getAuthor());
	}

	private void insertDummyProduct() {
		Product dummy = dummyProduct(1);
		productDao.createProduct(dummy.getName(), dummy.getDescription(), dummy.getShortDescription(), 
				dummy.getWebsite(), dummy.getCategory(), dummy.getUploadDate(), dummy.getLogo(), dummy.getCreator());
	}

	private void insertDummyUser() throws DuplicateEmailException {
		User dummy = dummyUser(1);
		userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(), profilePictureFromUser(dummy));
	}
}
