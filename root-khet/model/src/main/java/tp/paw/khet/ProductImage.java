package tp.paw.khet;

public class ProductImage {
	private int productImageId;
	private int productId;
	private byte[] data;
	
	public ProductImage(int productImageId, int productId, byte[] data) {
		this.productImageId = productImageId;
		this.productId = productId;
		this.data = data;
	}

	public int getProductImageId() {
		return productImageId;
	}
	
	public int getProductId() {
		return productId;
	}

	public byte[] getData() {
		return data;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;
		
		ProductImage other = (ProductImage) obj;
		
		return getProductImageId() == other.getProductImageId() && getProductId() == other.getProductId();
	}
	
	@Override
	public int hashCode() {
		return getProductImageId() ^ getProductId();
	}
}
