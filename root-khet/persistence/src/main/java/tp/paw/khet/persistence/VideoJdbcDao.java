package tp.paw.khet.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.User;
import tp.paw.khet.Video;
import tp.paw.khet.persistence.rowmapper.VideoRowMapper;

@Repository
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

	public Video createVideo(String videoId, int productId) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("videoId", videoId);
		args.put("productId", productId);
		
		jdbcInsert.execute(args);
		
		return new Video(videoId, productId);
	}

}
