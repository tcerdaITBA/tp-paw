package tp.paw.khet.persistence;

import static org.junit.Assert.*;
import static tp.paw.khet.testutils.UserTestUtils.*;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
	
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
	public void createUserTest() {
		User expected = dummyUser(0);
		User actual = userDao.createUser(expected.getName(), expected.getEmail(), expected.getPassword());
		
		assertEqualsUsers(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
	
	@Test
	public void getUserByEmailTest() {
		User expected = dummyUser(0);
		userDao.createUser(expected.getName(), expected.getEmail(), expected.getPassword());
		
		User actual = userDao.getUserByEmail(expected.getEmail());
		
		assertEqualsUsers(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
}
