package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static tp.paw.khet.model.validate.PrimitiveValidation.notEmptyByteArray;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import tp.paw.khet.model.ProductImage.ProductImagePrimaryKeyIds;

@Entity
@IdClass(ProductImagePrimaryKeyIds.class)
@Table(name = "productimages")
public class ProductImage {
	
	@Id
	private int productImageId;
	
	@Id
	private int productId;
	
	@Column(nullable = false, columnDefinition = "bytea")
	private byte[] data;
	
	public ProductImage(int productImageId, int productId, byte[] data) {
		isTrue(productImageId >= 0, "Product image ID must be non negative: %d", productImageId);
		isTrue(productId >= 0, "Product ID must be non negative: %d", productId);
		
		this.productImageId = productImageId;
		this.productId = productId;
		this.data = notEmptyByteArray(data, "Image data array cannot be null", "Image data array cannot be empty");
	}
	
	// Hibernate
	ProductImage() {
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
	
	// For Hibernate Composite keys
	@SuppressWarnings("serial")
	public static class ProductImagePrimaryKeyIds implements Serializable {		
		private int productImageId;
		private int productId;
		
		public ProductImagePrimaryKeyIds(int productImageId, int productId) {
			this.productImageId = productImageId;
			this.productId = productId;
		}
		
		// Hibernate
		public ProductImagePrimaryKeyIds() {
		}
		
		public int getProductImageId() {
			return productImageId;
		}
		
		public int getProductId() {
			return productId;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			if (!(obj instanceof ProductImagePrimaryKeyIds))
				return false;
			
			final ProductImagePrimaryKeyIds other = (ProductImagePrimaryKeyIds) obj;
			
			return productId == other.productId && productImageId == other.productImageId;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 17;
			
			result = result * prime + getProductImageId();
			result = result * prime + getProductId();
			
			return result;
		}
	}
}
