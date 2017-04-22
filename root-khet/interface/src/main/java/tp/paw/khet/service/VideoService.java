package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.Product;
import tp.paw.khet.Video;

public interface VideoService {
	
	/**
	 * Creates a {@link Video} associated with a {@link Product}.
	 * @param videoId - ID of the video
	 * @param productId - ID of the product the video belongs to
	 * @return The created {@link Video}
	 */
	public Video createVideo(String videoId, int productId);
	
	/**
	 * Lists videos for a especific {@link Product}.
	 * @param productId - ID of the product the video is associated with
	 * @return The {@link List} of the product's videos
	 */
	public List<Video> getVideosByProductId(int id);
}
