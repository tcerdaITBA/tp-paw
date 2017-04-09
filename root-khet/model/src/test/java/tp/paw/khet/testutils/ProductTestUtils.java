package tp.paw.khet.testutils;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tp.paw.khet.Product;

public final class ProductTestUtils {
	
	private ProductTestUtils() {
	}
	
	public static Product dummyProduct(int id) {
		return new Product(id, "Product " + id, "Description " + id, "Short Description " + id, LocalDateTime.now().plusSeconds(id));
	}
	
	public static List<Product> dummyProductList(int size, int initialId) {
		List<Product> productList = new ArrayList<Product>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProduct(initialId + i));
		
		return productList;
	}
	
	public static void assertEqualsProducts(Product expected, Product actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getShortDescription(), actual.getShortDescription());
		assertEquals(expected.getUploadDate(), actual.getUploadDate());
	}
	
	public static byte[] logoFromProduct(Product product) {
		return product.getName().getBytes();
	}
}
