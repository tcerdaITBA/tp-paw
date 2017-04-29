package tp.paw.khet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Video;
import tp.paw.khet.persistence.VideoDao;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao videoDao;
	
	@Override
	public List<Video> getVideosByProductId(int id) {
		return videoDao.getVideosByProductId(id);
	}
	
	@Override
	public Video createVideo(String videoId, int productId) {
		return videoDao.createVideo(videoId, productId);
	}

	@Override
	public List<Video> createVideos(List<String> videoIds, int productId) {
		List<Video> videos = new ArrayList<>();
		
		for (String videoId : videoIds)
			videos.add(createVideo(videoId, productId));
		
		return null;
	}

}
