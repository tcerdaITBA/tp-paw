package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.ProductImage;

public interface ProductImageDao {
	public List<ProductImage> getImagesByProductId(int productId);
	public ProductImage createProductImage(int imageId, int productId, byte[] data);	
}