package tp.paw.khet.persistence;

import java.util.List;

import tp.paw.khet.ProductImage;
import tp.paw.khet.Product;

public interface ProductImageDao {
	
	/**
	 * Creates an {@link ProductImage} for a {@link Product} inserting it into the database.
	 * @param imageId - ID of the new image
	 * @param productId - ID of the product the image belongs to
	 * @return The created ProductImage
	 */
	public ProductImage createProductImage(int imageId, int productId, byte[] data);	
	
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