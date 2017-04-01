package tp.paw.khet.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.User;

@Repository
public class UserJdbcDao implements UserDao {
	
	private JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public UserJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("users")
					.usingGeneratedKeyColumns("userid");		
	}
	
	public User createUser(String userName, String email) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", userName);
		args.put("mailAddr", email);
		
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		
		return new User(userId.intValue(), userName, email);
	}

}
