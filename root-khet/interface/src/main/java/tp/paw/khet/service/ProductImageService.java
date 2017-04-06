package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.ProductImage;

public interface ProductImageService {
	/**
	 * Lists images ID of a Product given its ID
	 * @param productId - ID of the product
	 * @return list with the product images ID
	 */
	public List<Integer> getImagesIdByProductId(int productId);
	
	/**
	 * Gets an {@link ProductImage} for a Product 
	 * @param imageId
	 * @param productId
	 * @return ProductImage
	 */
	public ProductImage getImageByIds(int imageId, int productId);
	
	/**
	 * Creates an {@link ProductImage} for a Product 
	 * @param imageId
	 * @param productId
	 * @return ProductImage
	 */
	public ProductImage createProductImage(int imageId, int productId, byte[] data);
}
