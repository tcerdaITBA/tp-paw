package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Video;

@Component
public class VideoRowMapper implements RowMapper<Video> {
	public Video mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		return new Video(rs.getString("videoId"), rs.getInt("productId"));
	}
}