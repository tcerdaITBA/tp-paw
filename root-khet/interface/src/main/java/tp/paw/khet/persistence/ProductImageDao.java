package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.ProductImage;

public interface ProductImageDao {
	
	/**
	 * Lists images ID of a {@link Product} given its ID
	 * @param productId - ID of the product
	 * @return list with the product images ID
	 */
	public List<Integer> getImagesIdByProductId(int productId);
	
	/**
	 * Gets an {@link ProductImage} for a {@link Product} 
	 * @param imageId
	 * @param productId
	 * @return ProductImage
	 */
	public ProductImage getImageByIds(int imageId, int productId);
	
	/**
	 * Creates an {@link ProductImage} for a {@link Product} 
	 * @param imageId
	 * @param productId
	 * @return ProductImage
	 */
	public ProductImage createProductImage(int imageId, int productId, byte[] data);	
}