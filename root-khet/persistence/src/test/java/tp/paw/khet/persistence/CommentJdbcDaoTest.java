package tp.paw.khet.persistence;

import static org.junit.Assert.*;
import static tp.paw.khet.testutils.ProductTestUtils.dummyProduct;
import static tp.paw.khet.testutils.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.testutils.UserTestUtils.dummyUser;

import java.util.List;

import static tp.paw.khet.testutils.CommentTestUtils.*;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import tp.paw.khet.Comment;
import tp.paw.khet.Product;
import tp.paw.khet.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class CommentJdbcDaoTest {

	@Autowired
	private CommentJdbcDao commentDao;
	
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
	public void createRootCommentTest() {
		Comment expected = dummyRootComment(0);
		Comment actual = insertComment(expected, 0, 0);
		
		assertEqualsComments(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "comments"));
	}
	
	@Test
	public void createChildCommentTest() {
		insertComment(dummyRootComment(0), 0, 0).getId();
		Comment expected = dummyComment(1, 0);
		Comment actual = insertComment(expected, 0, 0);

		assertEqualsComments(expected, actual);
		assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "comments"));
	}
	
	@Test
	public void getCommentsByProductIdTest() {
		insertCommentList(dummyRootCommentList(7, 0), 0, 0);
		
		for (int i = 0; i < 7; i++)
			insertCommentList(dummyCommentList(5, 7 + i * 5, i), 0, 0);
		
		List<Comment> actual = commentDao.getCommentsByProductId(0);
		
		assertCommentsOrder(actual);
	}
	
	private void insertCommentList(List<Comment> comments, int productId, int userId) {
		for (Comment comment : comments)
			insertComment(comment, productId, userId);
	}

	// Asserts that a block of root comments come first, then a block of child comments with parentId of the first root comment and so on
	private void assertCommentsOrder(List<Comment> comments) {
		assertNull(comments.get(0).getParentId());
		
		for (int i = 0, j = childrenPointer(comments); j < comments.size(); j++) {
			Comment parent = comments.get(i);
			Comment child = comments.get(j);
			
			assertNull(parent.getParentId());
			
			if (child.getParentId() > parent.getId()) {
				i++;
			}
			else {
				assertEquals(Integer.valueOf(parent.getId()), child.getParentId());
				if (j < comments.size() - 1 && child.getParentId().equals(comments.get(j+1).getParentId()))
					assertTrue(child.getCommentDate().compareTo(comments.get(j+1).getCommentDate()) < 0); // Oldest comments first
			}
		}
	}
	
	private int childrenPointer(List<Comment> comments) {
		for (int i = 0; i < comments.size(); i++) {
			if (comments.get(i).getParentId() != null)
				return i;
			
			if (i < comments.size()-1 && comments.get(i+1).getParentId() != null)
				assertTrue(comments.get(i).getCommentDate().compareTo(comments.get(i+1).getCommentDate()) < 0); // Oldest comments first
		}
		
		return comments.size();
	}
	
	private Comment insertComment(Comment comment, int productId, int userId) {
		return commentDao.createComment(comment.getContent(), comment.getCommentDate(), comment.getParentId(), productId, userId);
	}

	private void insertDummyProduct() {
		Product dummy = dummyProduct(0);
		productDao.createProduct(dummy.getName(), dummy.getDescription(), dummy.getShortDescription(), 
				dummy.getUploadDate(), logoFromProduct(dummy), 0);
	}

	private void insertDummyUser() {
		User dummy = dummyUser(0);
		userDao.createUser(dummy.getName(), dummy.getEmail());
	}
}
