package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.Video;

public interface VideoDao {
	public List<Video> getVideosByProductId(int id);
	public Video createVideo(String videoId, int productId);
}
