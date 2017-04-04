package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.ProductImage;

public interface ProductImageService {
	public List<ProductImage> getImagesByProductId(int productId);
	public ProductImage createProductImage(int imageId, int productId, byte[] data);
}
