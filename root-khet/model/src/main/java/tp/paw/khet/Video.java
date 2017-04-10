package tp.paw.khet;

public class Video {
	private String videoId;
	private int productId;
	
	public Video(String videoId, int productId) {
		if (videoId == null)
			throw new IllegalArgumentException("videoId may not be null");
		
		this.videoId = videoId;
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