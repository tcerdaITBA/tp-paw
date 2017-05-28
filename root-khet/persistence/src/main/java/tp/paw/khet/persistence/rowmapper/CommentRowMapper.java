package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.model.Comment;
import tp.paw.khet.model.User;

@Component
public class CommentRowMapper implements RowMapper<Comment> {
	
	@Autowired
	private UserRowMapper userRowMapper;
	
	public Comment mapRow(final ResultSet rs, final int rowNum) throws SQLException {		
		final User user = userRowMapper.mapRow(rs, rowNum);
		
		final Integer parentId = rs.getInt("parentId");
		if (rs.wasNull())
			return new Comment(rs.getInt("commentId"), user, rs.getString("commentContent"), rs.getTimestamp("commentDate").toLocalDateTime());		
		
		return new Comment(rs.getInt("commentId"), parentId, user, 
				rs.getString("commentContent"), rs.getTimestamp("commentDate").toLocalDateTime());
	}
}
