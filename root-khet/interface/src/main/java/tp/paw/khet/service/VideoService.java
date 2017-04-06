package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Video;

public interface VideoService {
	/**
	 * Lists {@link Video}s for a especific Product
	 * @param id - Product ID
	 * @return list of the product's videos
	 */
	public List<Video> getVideosByProductId(int id);
	
	/**
	 * Creates a {@link Video} for a Product
	 * @param videoId - ID of the video
	 * @param productId
	 * @return video
	 */
	public Video createVideo(String videoId, int productId);
}
