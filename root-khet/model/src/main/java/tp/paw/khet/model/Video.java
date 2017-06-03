package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import tp.paw.khet.model.Video.VideoPrimaryKeyIds;

@Entity
@IdClass(VideoPrimaryKeyIds.class)
@Table(name = "videos")
public class Video {
	@Id
	@Column(length = 11, nullable = false)
	private String videoId;
	
	@Id
	private int productId;
	
	// Hibernate
	Video() {
	}
	
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
		
		return getProductId() == other.getProductId() && getVideoId().equals(other.getVideoId());
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
	
	// For Hibernate Composite keys
	@SuppressWarnings("serial")
	public static class VideoPrimaryKeyIds implements Serializable {
		private String videoId;
		private int productId;
		
		public VideoPrimaryKeyIds() {
		}
		
		public VideoPrimaryKeyIds(String videoId, int productId) {
			this.videoId = videoId;
			this.productId = productId;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof VideoPrimaryKeyIds))
				return false;
			
			VideoPrimaryKeyIds other = (VideoPrimaryKeyIds) obj;
			
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
	}
}