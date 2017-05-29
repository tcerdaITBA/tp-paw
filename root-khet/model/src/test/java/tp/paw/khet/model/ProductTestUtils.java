package tp.paw.khet.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static tp.paw.khet.model.UserTestUtils.dummyUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ProductTestUtils {
	
	private ProductTestUtils() {
	}
	
	public static Product dummyProduct(int id) {
		return dummyProductBuilder(id).build();
	}
	
	public static Product dummyProductWithUserId(int id, int userId) {
		return dummyProductBuilder(id).creator(dummyUser(userId)).build();
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
		String name = "Product " + id;
		LocalDateTime ldt = LocalDateTime.now().plusSeconds(id);
		return Product.getBuilder(name, "Short Description " + id)
				.id(id)
				.description("Description " + id)
				.website("http://www.productseek.com/" + id)
				.creator(dummyUser(id))
				.logo(name.getBytes())
				.uploadDate(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
	}
	
	public static List<Product> dummyProductList(int size, int initialId) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProduct(initialId + i));
		
		return productList;
	}
	
	public static List<Product> dummyPlainProductList(int size, int initialId) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProduct(initialId + i));
		
		return productList;
	}

	public static List<Product> dummyProductListWithUserId(int size, int initialId, int userId) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProductWithUserId(initialId + i, userId));
		
		return productList;
	}
	
	public static List<Product> dummyPlainProductListWithUserId(int size, int initialId, int userId) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProductBuilder(initialId + i).creator(dummyUser(userId)).build());
		
		return productList;
	}
	
	public static List<Product> dummyPlainProductListWithCategory(int size, int initialId, Category category) {
		List<Product> productList = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			productList.add(dummyProductWithCategory(initialId + i, category));
		
		return productList;
	}

	
	public static void assertEqualsFullProducts(Product expected, Product actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getName(), actual.getName());
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getWebsite(), actual.getWebsite());
		assertEquals(expected.getShortDescription(), actual.getShortDescription());
		assertEquals(expected.getCategory(), actual.getCategory());
		assertArrayEquals(expected.getLogo(), actual.getLogo());
		assertEquals(expected.getCreator(), actual.getCreator());
	}
	
	public static void assertEqualsPlainProducts(Product expected, Product actual) {
		assertEquals(expected, actual);
		assertEquals(expected.getShortDescription(), actual.getShortDescription());
		assertEquals(expected.getCategory(), actual.getCategory());
	}
	
	public static byte[] logoFromProduct(Product product) {
		return product.getName().getBytes();
	}
}
