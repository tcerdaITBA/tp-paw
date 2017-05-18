package tp.paw.khet.persistence;

import static org.junit.Assert.*;
import static tp.paw.khet.testutils.UserTestUtils.*;

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
import org.springframework.test.jdbc.JdbcTestUtils;

import tp.paw.khet.User;
import tp.paw.khet.exception.DuplicateEmailException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
	
	private final static int LIST_SIZE = 20;
	
	@Autowired
	private UserJdbcDao userDao;
	
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
		User expected = dummyUser(0);
		User actual = userDao.createUser(expected.getName(), expected.getEmail(), expected.getPassword(), profilePictureFromUser(expected));
		
		assertEqualsUsers(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
	
	@Test(expected = DuplicateEmailException.class)
	public void duplicateEmailExceptionTest() throws DuplicateEmailException {
		User dummyUser = dummyUser(0);
		insertUser(dummyUser);
		insertUser(dummyUser);
	}
	
	@Test
	public void getUserByEmailTest() throws DuplicateEmailException {
		User expected = dummyUser(0);
		insertUser(expected);
		
		User actual = userDao.getUserByEmail(expected.getEmail());
		
		assertEqualsUsers(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
	
	@Test
	public void getUserByIdTest() throws DuplicateEmailException {
		User expected = dummyUser(0);
		insertUser(expected);
		
		User actual = userDao.getUserById(expected.getUserId());
		
		assertEqualsUsers(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
	
	@Test
	public void getProfilePictureFromIdTest() throws DuplicateEmailException {
		User dummyUser = dummyUser(0);
		byte[] expected = profilePictureFromUser(dummyUser);
		userDao.createUser(dummyUser.getName(), dummyUser.getEmail(), dummyUser.getPassword(), expected);
		
		byte[] actual = userDao.getProfilePictureByUserId(dummyUser.getUserId());
		
		assertArrayEquals(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
	
	@Test
	public void getUserByKeywordTest() throws DuplicateEmailException {
		List<User> expected = dummyUserList(LIST_SIZE, 0);
		insertUsers(expected);
		String keyword = expected.get(0).getName().substring(0, 3);

		List<User> actual = userDao.getUsersByKeyword(keyword);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		
		actual = userDao.getUsersByKeyword("cerd");
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));

		assertTrue(userDao.getUsersByKeyword("sucutrule").isEmpty());
		
		assertEqualsUsers(dummyUser(0), userDao.getUsersByKeyword("0").get(0));
	}
	
	private void insertUser(User user) throws DuplicateEmailException {
		userDao.createUser(user.getName(), user.getEmail(), user.getPassword(), profilePictureFromUser(user));
	}
	
	private void insertUsers(List<User> users) throws DuplicateEmailException {
		for (User user : users)
			insertUser(user);
	}
}
