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
import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.persistence.rowmapper.UserRowMapper;

@Repository
public class UserJdbcDao implements UserDao {
		
	@Autowired
	private UserRowMapper userRowMapper;
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public UserJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("users")
					.usingGeneratedKeyColumns("userid");		
	}
	
	@Override
	public User createUser(String userName, String email, String password, byte[] profilePicture) throws DuplicateEmailException {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("userName", userName);
		args.put("email", email);
		args.put("password", password);
		args.put("profilePicture", profilePicture);

		try {
			final Number userId = jdbcInsert.executeAndReturnKey(args);
			return new User(userId.intValue(), userName, email, password);
		} 
		catch (DuplicateKeyException e) {
			throw new DuplicateEmailException("There already exists an user with email: " + email);
		}
	}
	
	@Override
	public User getUserByEmail(String email) {
		List<User> user = jdbcTemplate.query("SELECT userId, userName, email, password FROM users WHERE email = ?", userRowMapper, email);
		
		if (user.isEmpty())
			return null;
		
		return user.get(0);
	}

	@Override
	public User getUserById(int userId) {
		List<User> user = jdbcTemplate.query("SELECT userId, userName, email, password FROM users WHERE userid = ?", userRowMapper, userId);
		
		if (user.isEmpty())
			return null;
		
		return user.get(0);
	}

	@Override
	public byte[] getProfilePictureByUserId(int userId) {
		byte[] profilePicture = jdbcTemplate.queryForObject("SELECT profilePicture FROM users WHERE userId = ?", byte[].class, userId);
		
		if (profilePicture == null)
			return new byte[0];
		
		return profilePicture;
	}

	@Override
	public List<User> getUsersByKeyword(String keyword, int maxLength) {
		String firstWordKeyword = keyword+"%";
		String otherWordsKeyword = "% "+keyword+"%";
		String sql = "SELECT userId, userName, email, password FROM users WHERE "
					 + "lower(userName) LIKE lower(?) OR lower(userName) LIKE lower(?) "
					 + "ORDER BY userName LIMIT ?";

		return jdbcTemplate.query(sql, userRowMapper, firstWordKeyword, otherWordsKeyword, maxLength);
	}
	
	public User changePassword(int userId, String password) {
		User user = getUserById(userId);
		
		if (user != null)
			jdbcTemplate.update("UPDATE users SET password = ? WHERE userId = ?", password, userId);
		
		return user;
	}

	public User changeProfilePicture(int userId, byte[] profilePicture) {
		User user = getUserById(userId);
		
		if (user != null)
			jdbcTemplate.update("UPDATE users SET profilePicture = ? WHERE userId = ?", profilePicture, userId);
		
		return user;
	}
}
