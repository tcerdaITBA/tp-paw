package tp.paw.khet;

public class Video {
	private String videoId;
	private int productId;
	
	public Video(String videoId, int productId) {
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Video other = (Video) obj;
		
		return productId == other.productId && videoId == other.videoId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		result = prime * result + ((videoId == null) ? 0 : videoId.hashCode());
		return result;
	}
}