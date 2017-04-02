package tp.paw.khet.persistence.rowmapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import tp.paw.khet.Video;

public class VideoRowMapper implements RowMapper<Video> {

	private final static VideoRowMapper INSTANCE = new VideoRowMapper();
	
	public final static VideoRowMapper getInstance() {
		return INSTANCE;
	}
	
	private VideoRowMapper() {
	}
	
	public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Video(rs.getString("videoId"), rs.getInt("productId"));
	}
}