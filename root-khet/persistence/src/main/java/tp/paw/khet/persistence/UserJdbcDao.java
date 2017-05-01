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
	
	@Override
	public User createUser(String userName, String email, String password) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", userName);
		args.put("email", email);
		args.put("password", password);

		try {
			final Number userId = jdbcInsert.executeAndReturnKey(args);
			return new User(userId.intValue(), userName, email, password);
		} 
		catch (DuplicateKeyException e) {
			return null;
		}
	}
	
	@Override
	public User getUserByEmail(String email) {
		List<User> user = jdbcTemplate.query("SELECT * FROM users WHERE email = ?", userRowMapper, email);
		
		if (user.isEmpty())
			return null;
		
		return user.get(0);
	}

	@Override
	public User getUserById(int userId) {
		List<User> user = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", userRowMapper, userId);
		
		if (user.isEmpty())
			return null;
		
		return user.get(0);
	}
}
