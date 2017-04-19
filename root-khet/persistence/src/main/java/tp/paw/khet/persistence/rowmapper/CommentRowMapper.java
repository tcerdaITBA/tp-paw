package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Comment;
import tp.paw.khet.User;

@Component
public class CommentRowMapper implements RowMapper<Comment> {
	
	@Autowired
	private UserRowMapper userRowMapper;
	
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {		
		User user = userRowMapper.mapRow(rs, rowNum);
		
		Integer parentId = rs.getInt("parentId");
		if (rs.wasNull())
			return new Comment(rs.getInt("commentId"), user, rs.getString("commentContent"), rs.getTimestamp("commentDate").toLocalDateTime());		
		
		return new Comment(rs.getInt("commentId"), parentId, user, 
				rs.getString("commentContent"), rs.getTimestamp("commentDate").toLocalDateTime());
	}
}
