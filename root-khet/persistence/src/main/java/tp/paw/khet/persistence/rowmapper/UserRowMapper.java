package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tp.paw.khet.User;

public class UserRowMapper implements RowMapper<User> {

	private final static UserRowMapper INSTANCE = new UserRowMapper();
	
	public final static UserRowMapper getInstance() {
		return INSTANCE;
	}
	
	private UserRowMapper() {
	}
	
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("mailAddr"));
	}

}
