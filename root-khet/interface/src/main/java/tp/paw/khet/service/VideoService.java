package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Video;

public interface VideoService {
	public List<Video> getVideosByProductId(int id);
	public Video createVideo(String videoId, int productId);
}
