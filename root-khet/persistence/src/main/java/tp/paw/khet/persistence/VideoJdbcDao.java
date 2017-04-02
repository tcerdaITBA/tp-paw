package tp.paw.khet.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import tp.paw.khet.Video;
import tp.paw.khet.persistence.rowmapper.VideoRowMapper;

public class VideoJdbcDao implements VideoDao {
	
	private final static VideoRowMapper VIDEO_ROW_MAPPER = VideoRowMapper.getInstance();
	
	private JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public VideoJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("videos");
		}
	

	public List<Video> getVideosByProductId(int id) {
		List<Video> videos = jdbcTemplate.query("SELECT videoId, productId FROM videos WHERE productId = ?", VIDEO_ROW_MAPPER, id);
		return videos;
	}

	public Video createVideo(List<String> videos, int productId) {
		// TODO Auto-generated method stub
		return null;
	}

}
