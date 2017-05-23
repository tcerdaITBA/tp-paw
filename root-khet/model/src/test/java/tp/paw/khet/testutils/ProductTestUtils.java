package tp.paw.khet.testutils;

import static org.junit.Assert.assertEquals;
import static tp.paw.khet.testutils.UserTestUtils.dummyUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.interfaces.PlainProduct;

public final class ProductTestUtils {
	
	private ProductTestUtils() {
	}
	
	public static Product dummyProduct(int id) {
		return dummyProductBuilder(id).build();
	}

	public static Product dummyProductWithCategory(int id, Category category) {
		return productBuilder(id).category(category).build();
	}
	
	public static Product.ProductBuilder dummyProductBuilder(int id) {
		Category[] categories = Category.values();
		int len = categories.length;
		
		return productBuilder(id).category(categories[id % len]);
	}
	
	private static Product.ProductBuilder productBuilder(int id) {
		return Product.getBuilder(id, "Product " + id, "Short Description " + id)
				.description("Description " + id)
				.website("http://www.productseek.com/" + id)
				.creator(dummyUser(id))
				.uploadDate(LocalDateTime.now().plusSeconds(id));
	}
	
	public static List<Product> dummyProductList(int size, int initialId) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProduct(initialId + i));
		
		return productList;
	}
	
	public static List<PlainProduct> dummyPlainProductList(int size, int initialId) {
		List<PlainProduct> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProduct(initialId + i));
		
		return productList;
	}

	public static List<Product> dummyProductListWithUserId(int size, int initialId, int userId) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProductBuilder(initialId + i).creator(dummyUser(userId)).build());
		
		return productList;
	}
	
	public static List<PlainProduct> dummyPlainProductListWithUserId(int size, int initialId, int userId) {
		List<PlainProduct> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProductBuilder(initialId + i).creator(dummyUser(userId)).build());
		
		return productList;
	}
	
	public static List<PlainProduct> dummyPlainProductListWithCategory(int size, int initialId, Category category) {
		List<PlainProduct> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProductWithCategory(initialId + i, category));
		
		return productList;
	}

	
	public static void assertEqualsFullProducts(Product expected, Product actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getWebsite(), actual.getWebsite());
		assertEquals(expected.getShortDescription(), actual.getShortDescription());
		assertEquals(expected.getCategory(), actual.getCategory());
	}
	
	public static void assertEqualsPlainProducts(PlainProduct expected, PlainProduct actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getShortDescription(), actual.getShortDescription());
		assertEquals(expected.getCategory(), actual.getCategory());
	}
	
	public static byte[] logoFromProduct(Product product) {
		return product.getName().getBytes();
	}
}
