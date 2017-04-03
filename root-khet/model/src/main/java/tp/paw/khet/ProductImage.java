package tp.paw.khet;

public class ProductImage {
	private int imageId;
	private byte[] data;
	
	public ProductImage(int imageId, byte[] data) {
		this.imageId = imageId;
		this.data = data;
	}

	public int getImageId() {
		return imageId;
	}

	public byte[] getData() {
		return data;
	}
}
