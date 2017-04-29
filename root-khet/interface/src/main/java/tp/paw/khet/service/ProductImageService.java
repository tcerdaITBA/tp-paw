package tp.paw.khet.service;

import java.util.List;

import tp.paw.khet.ProductImage;
import tp.paw.khet.Product;

public interface ProductImageService {
	
	/**
	 * Creates an {@link ProductImage} for a {@link Product}.
	 * @param imageId - ID of the new image
	 * @param productId - ID of the product the image belongs to
	 * @param data - byte array of the image data
	 * @return The created product image
	 */
	public ProductImage createProductImage(int imageId, int productId, byte[] data);
	
	/**
	 * Creates a {@link List} of {@link ProductImage} associated with a {@link Product}
	 * @param imageBytes - list of byte arrays of the images data
	 * @param productId - ID of the product the images belong to
	 * @return The created list of product images
	 */
	public List<ProductImage> createProductImages(List<byte[]> imageBytes, int productId);
	
	/**
	 * Lists images ID of a {@link Product}.
	 * @param productId - ID of the product
	 * @return {@link List} with the product images ID. Could be empty if the product possesses no images
	 */
	public List<Integer> getImagesIdByProductId(int productId);
	
	/**
	 * Retrieves a {@link ProductImage} associated with a {@link Product}.
	 * @param imageId - ID of the image
	 * @param productId - ID of the product the image belongs to
	 * @return The image associated with the given IDs or null if it doesn't exist
	 */
	public ProductImage getImageByIds(int imageId, int productId);
}
