package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Comment;
import tp.paw.khet.CommentAndCommenter;
import tp.paw.khet.User;

@Component
public class CommentAndCommenterRowMapper implements RowMapper<CommentAndCommenter>{
	
	@Autowired
	private UserRowMapper userRowMapper;
	
	@Autowired
	private CommentRowMapper commentRowMapper;

	@Override
	public CommentAndCommenter mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = userRowMapper.mapRow(rs, rowNum);
		Comment comment = commentRowMapper.mapRow(rs, rowNum);
		
		return new CommentAndCommenter(comment, user);
	}

}
