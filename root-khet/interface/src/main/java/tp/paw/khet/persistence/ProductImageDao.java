package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.ProductImage;

public interface ProductImageDao {
	public List<Integer> getImagesIdByProductId(int productId);
	public ProductImage getImageByIds(int imageId, int productId);
	public ProductImage createProductImage(int imageId, int productId, byte[] data);	
}