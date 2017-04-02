package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import tp.paw.khet.Video;
import tp.paw.khet.persistence.VideoDao;

public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoDao videoDao;
	
	public List<Video> getVideosByProductId(int id) {
		return videoDao.getVideosByProductId(id);
	}
	
	public Video createVideo(List<String> videos, int productId) {
		return videoDao.createVideo(videos, productId);
	}

}
