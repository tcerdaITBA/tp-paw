package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Comment;

@Component
public class CommentRowMapper implements RowMapper<Comment> {
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer parentId = rs.getInt("parentId");
		
		if (rs.wasNull())
			return new Comment(rs.getInt("commentId"), rs.getString("commentContent"), rs.getTimestamp("commentDate").toLocalDateTime());		
		
		return new Comment(rs.getInt("commentId"), parentId, 
				rs.getString("commentContent"), rs.getTimestamp("commentDate").toLocalDateTime());
	}
}
