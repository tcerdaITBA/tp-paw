package tp.paw.khet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.model.Video;
import tp.paw.khet.persistence.VideoDao;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao videoDao;
	
	@Override
	public List<Video> getVideosByProductId(final int id) {
		return videoDao.getVideosByProductId(id);
	}
	
	@Transactional
	@Override
	public Video createVideo(final String videoId, final int productId) {
		return videoDao.createVideo(videoId, productId);
	}

	@Transactional
	@Override
	public List<Video> createVideos(final List<String> videoIds, final int productId) {
		List<Video> videos = new ArrayList<>();
		
		for (String videoId : videoIds)
			videos.add(createVideo(videoId, productId));
		
		return videos;
	}

}
