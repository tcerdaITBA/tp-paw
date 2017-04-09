package tp.paw.khet.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.User;
import tp.paw.khet.persistence.rowmapper.UserRowMapper;

@Repository
public class UserJdbcDao implements UserDao {
	
	@Autowired
	private UserRowMapper userRowMapper;
	
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

		try {
			final Number userId = jdbcInsert.executeAndReturnKey(args);
			return new User(userId.intValue(), userName, email);
		} 
		catch (DuplicateKeyException e) {
			return null;
		}
	}
	
	public User getUserByEmail(String email) {
		List<User> user = jdbcTemplate.query("SELECT * FROM users WHERE mailAddr = ?", userRowMapper, email);
		
		if (user.isEmpty())
			return null;
		
		return user.get(0);
	}

}
