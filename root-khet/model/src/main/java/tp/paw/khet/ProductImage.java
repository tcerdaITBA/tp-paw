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
		if (!(obj instanceof ProductImage))
			return false;
		
		ProductImage other = (ProductImage) obj;
		
		return getProductImageId() == other.getProductImageId() && getProductId() == other.getProductId();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		
		result = result * prime + productImageId;
		result = result * prime + productId;
		
		return result;
	}
	
	@Override
	public String toString() {
		return "Image " + productImageId + " from product " + productId;
	}
}
