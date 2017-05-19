package tp.paw.khet;

import static org.apache.commons.lang3.Validate.isTrue;

public class ProductImage {
	private final int productImageId;
	private final int productId;
	private final byte[] data;
	
	public ProductImage(int productImageId, int productId, byte[] data) {
		isTrue(productImageId >= 0, "Product image ID must be non negative: %d", productImageId);
		isTrue(productId >= 0, "Product ID must be non negative: %d", productId);
		
		this.productImageId = productImageId;
		this.productId = productId;
		this.data = notEmptyData(data);
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
	
	private byte[] notEmptyData(byte[] data) {
		if (data == null)
			throw new NullPointerException("Data array cannot be null");
		if (data.length == 0)
			throw new IllegalArgumentException("Data array cannot be empty");
		return data;
	}
}
