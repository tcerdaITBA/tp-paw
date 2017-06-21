package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.Video;

public interface VideoService {

	/**
	 * Creates a {@link Video} associated with a {@link Product}.
	 * 
	 * @param videoId
	 *            - ID of the video
	 * @param productId
	 *            - ID of the product the video belongs to
	 * @return The created video
	 */
	public Video createVideo(String videoId, int productId);

	/**
	 * Creates a {@link List} of {@link Videos} associated with a
	 * {@link Product}
	 * 
	 * @param videoIds
	 *            - List composed by IDs of the videos
	 * @param productId
	 *            - ID of the product the videos belong to
	 * @return The created list of videos
	 */
	public List<Video> createVideos(List<String> videoIds, int productId);

	/**
	 * Lists videos for a especific {@link Product}.
	 * 
	 * @param productId
	 *            - ID of the product the video is associated with
	 * @return The {@link List} of the product's videos
	 */
	public List<Video> getVideosByProductId(int productId);

}
