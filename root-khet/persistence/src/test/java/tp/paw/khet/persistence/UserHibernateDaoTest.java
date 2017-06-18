package tp.paw.khet.persistence;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tp.paw.khet.model.UserTestUtils.assertEqualsUsers;
import static tp.paw.khet.model.UserTestUtils.dummyUser;
import static tp.paw.khet.model.UserTestUtils.dummyUserList;
import static tp.paw.khet.model.UserTestUtils.profilePictureFromUser;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import tp.paw.khet.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
public class UserHibernateDaoTest {
	
	private final static int LIST_SIZE = 20;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserHibernateDao userDao;
	
	@Autowired
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
	}

	@Test
	public void createUserTest() throws DuplicateEmailException {
		User expected = dummyUser(1);
		User actual = userDao.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), profilePictureFromUser(expected));
		
		assertEqualsUsers(expected, actual);
	}
	
	@Test(expected = DuplicateEmailException.class)
	public void duplicateEmailExceptionTest() throws DuplicateEmailException {
		User dummyUser = dummyUser(1);
		insertUser(dummyUser);
		insertUser(dummyUser);
	}
	
	@Test
	public void getUserByEmailTest() throws DuplicateEmailException {
		User expected = dummyUser(1);
		insertUser(expected);
		
		User actual = userDao.getUserByEmail(expected.getEmail());
		
		assertEqualsUsers(expected, actual);
	}
	
	@Test
	public void getUserByIdTest() throws DuplicateEmailException {
		User expected = dummyUser(1);
		insertUser(expected);
		
		User actual = userDao.getUserById(expected.getUserId());
		
		assertEqualsUsers(expected, actual);
	}
	
	@Test
	@Transactional
	public void getProfilePictureFromIdTest() throws DuplicateEmailException {
		User dummyUser = dummyUser(1);
		byte[] expected = profilePictureFromUser(dummyUser);
		userDao.createUser(dummyUser.getName(), dummyUser.getEmail(), dummyUser.getPassword(), expected);
		
		byte[] actual = userDao.getProfilePictureByUserId(dummyUser.getUserId());
		
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void getUserByKeywordTest() throws DuplicateEmailException {
		List<User> expected = dummyUserList(LIST_SIZE, 1);
		insertUsers(expected);
		String keyword = expected.get(0).getName().substring(0, 3);

		List<User> actual = userDao.getUsersByKeyword(keyword, LIST_SIZE);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		assertSortedByUsername(actual);
		
		actual = userDao.getUsersByKeyword("cerd", LIST_SIZE);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		assertSortedByUsername(actual);
		
		expected = actual.subList(0, 5);
		actual = userDao.getUsersByKeyword(keyword, 5);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		assertSortedByUsername(actual);
		
		assertTrue(userDao.getUsersByKeyword("sucutrule", LIST_SIZE).isEmpty());
		
		assertEqualsUsers(dummyUser(1), userDao.getUsersByKeyword("1", LIST_SIZE).get(0));
	}
	
	private void assertSortedByUsername(List<User> actual) {
		for (int i = 1; i < actual.size(); i++)
			assertTrue(actual.get(i).getName().compareTo(actual.get(i-1).getName()) > 0);
	}

	@Test
	public void changePasswordTest() throws DuplicateEmailException {
		User dummyUser = dummyUser(1);
		String expectedPassword = "sucutrule";
		User expected = new User(dummyUser.getName(), dummyUser.getEmail(), expectedPassword, profilePictureFromUser(dummyUser));
		
		User inserted = insertUser(dummyUser);
		
		User actual = userDao.changePassword(inserted.getUserId(), expectedPassword);
		
		assertEqualsUsers(inserted, actual);
		assertEquals(expectedPassword, expected.getPassword());
	}
	
	@Test
	public void changeProfilePictureTest() throws DuplicateEmailException {
		User dummyUser = dummyUser(1);
		byte[] expected = "Modified image".getBytes();

		User inserted = insertUser(dummyUser);
		
		userDao.changeProfilePicture(inserted.getUserId(), expected);
		byte[] actual = userDao.getProfilePictureByUserId(inserted.getUserId());
		
		assertArrayEquals(expected, actual);
	}
	
	public User insertUser(User user) throws DuplicateEmailException {
		return userDao.createUser(user.getName(), user.getEmail(), user.getPassword(), profilePictureFromUser(user));
	}
	
	private void insertUsers(List<User> users) throws DuplicateEmailException {
		for (User user : users)
			insertUser(user);
	}
}
