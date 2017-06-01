package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.Video;

public interface VideoDao {
	
	/**
	 * Creates a {@link Video} associated with a {@link Product}, inserting it into the database.
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
	public List<Video> getVideosByProductId(int productId);
}
