package tp.paw.khet.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import tp.paw.khet.model.ProductImage;

public final class ProductImageTestUtils {

	private ProductImageTestUtils() {
	}

	public static ProductImage dummyProductImage(int id, int productId) {
		return new ProductImage(id, productId, new byte[] { (byte) id, (byte) productId });
	}

	public static List<ProductImage> dummyProductImageList(int size, int initialImageId, int productId) {
		List<ProductImage> productImageList = new ArrayList<ProductImage>(size);

		for (int i = 0; i < size; i++)
			productImageList.add(dummyProductImage(initialImageId + i, productId));

		return productImageList;
	}

	public static void assertEqualsProductImages(ProductImage expected, ProductImage actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getProductImageId(), expected.getProductImageId());
		assertEquals(expected.getProductId(), expected.getProductId());
		assertArrayEquals(expected.getData(), expected.getData());
	}
}
