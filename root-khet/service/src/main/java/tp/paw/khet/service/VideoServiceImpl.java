package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.Video;
import tp.paw.khet.persistence.VideoDao;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao videoDao;
	
	public List<Video> getVideosByProductId(int id) {
		return videoDao.getVideosByProductId(id);
	}
	
	public Video createVideo(String videoId, int productId) {
		return videoDao.createVideo(videoId, productId);
	}

}
