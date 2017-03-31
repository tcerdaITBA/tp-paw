package tp.paw.khet.persistence;

import static org.junit.Assert.assertEquals;

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
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}

	@Test
	public void testCreate() {
		User expectedUser = new User(0, "Tomás Cerdá", "tcerda@itba.edu.ar");
		User createdUser = userDao.createUser(expectedUser.getName(), expectedUser.getMail());
		
		assertEquals(expectedUser.getUserId(), createdUser.getUserId());
		assertEquals(expectedUser.getName(), createdUser.getName());
		assertEquals(expectedUser.getMail(), createdUser.getMail());

		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}

}
