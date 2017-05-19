package tp.paw.khet;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.isTrue;

public class Video {
	private final String videoId;
	private final int productId;
	
	public Video(String videoId, int productId) {
		isTrue(productId >= 0, "Product ID must be non negative");
		
		this.videoId = notBlank(videoId, "Video ID must have at least one non blank character");
		this.productId = productId;
	}
	
	public String getVideoId() {
		return videoId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Video))
			return false;
		
		Video other = (Video) obj;
		
		return productId == other.productId && videoId.equals(other.videoId);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + productId;
		result = prime * result + videoId.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return videoId;
	}
}