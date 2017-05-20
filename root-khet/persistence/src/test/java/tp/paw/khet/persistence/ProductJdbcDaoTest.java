package tp.paw.khet.persistence;

import static org.junit.Assert.*;
import static tp.paw.khet.testutils.ProductTestUtils.*;
import static tp.paw.khet.testutils.UserTestUtils.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.exception.DuplicateEmailException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ProductJdbcDaoTest {

	private static final int LIST_SIZE = Category.values().length * 5;
	
	@Autowired
	private ProductJdbcDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
		insertDummyUser();
	}

	@Test
	public void getProductsTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 0);
		insertProducts(expected);
				
		List<Product> actual = productDao.getPlainProducts();
		
		assertEqualsReversedOrderedList(expected, actual);
		
		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getProductsByCategoryTest() {
		Category[] categories = Category.values();
		List<Product> productList = dummyProductList(LIST_SIZE, 0);
		insertProducts(productList);
		
		for (int i = 0; i < categories.length; i++)
			assertRetrievedCategory(categories[i], productList);

		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}

	@Test
	public void createProductTest() {
		Product expected = dummyProduct(0);
		Product actual = insertProduct(expected);
		
		assertEqualsFullProducts(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getProductByProductIdTest() {
		Product expected = dummyProduct(0);
		insertProduct(expected);
		
		Product actual = productDao.getFullProductById(0).build();
		
		assertEqualsFullProducts(expected, actual);
		assertNull(productDao.getFullProductById(1));
	}
	
	@Test
	public void getPlainProductByUserIdTest() {
		List<Product> expected = dummyProductListWithUserId(LIST_SIZE, 0, 0);
		insertProducts(expected);
		
		List<Product> actual = productDao.getPlainProductsByUserId(0);
		
		assertEqualsReversedOrderedList(expected, actual);
		
		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}

	@Test
	public void getLogoByProductIdTest() {
		Product dummyProduct = dummyProduct(0);
		insertProduct(dummyProduct);
		
		byte[] logo = productDao.getLogoByProductId(0);
		
		assertArrayEquals(logo, logoFromProduct(dummyProduct));
	}
	
	@Test
	public void deleteProductByUserId() {
		Product dummyProduct = dummyProduct(0);
		insertProduct(dummyProduct);

		assertTrue(productDao.deleteProductById(0));
		assertFalse(productDao.deleteProductById(0));
		
		assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getPlainProductsByKeywordMatchNameTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 0);
		insertProducts(expected);
		String keyword = expected.get(0).getName().substring(0, 3);
		String noMatchKeyword = expected.get(0).getName().substring(1, 3);

		assertSearch(keyword, noMatchKeyword, expected);
	}
	
	@Test
	public void getPlainProductsByKeywordMatchTaglineTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 0);
		insertProducts(expected);
		String keyword = expected.get(0).getShortDescription().substring(0, 3);
		String noMatchKeyword = expected.get(0).getShortDescription().substring(1, 3);

		assertSearch(keyword, noMatchKeyword, expected);
		List<Product> actual = productDao.getPlainProductsByKeyword("desc", LIST_SIZE);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
	}
	
	private void assertSearch(String keyword, String noMatchKeyword, List<Product> expected) {
		List<Product> actual = productDao.getPlainProductsByKeyword(keyword, LIST_SIZE);
		
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		
		expected = actual.subList(0, 5);
		actual = productDao.getPlainProductsByKeyword(keyword, 5);
				
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		
		assertTrue(productDao.getPlainProductsByKeyword("sucutrule", LIST_SIZE).isEmpty());
				
		assertTrue(productDao.getPlainProductsByKeyword(noMatchKeyword, LIST_SIZE).isEmpty());
		assertEqualsPlainProducts(dummyProduct(0), productDao.getPlainProductsByKeyword("0", LIST_SIZE).get(0));
	}
	
	private void insertDummyUser() throws DuplicateEmailException {
		List<User> list = dummyUserList(LIST_SIZE, 0);
		for (User dummy : list)
			userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(), profilePictureFromUser(dummy));
	}
	
	private Product insertProduct(Product product) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), product.getWebsite(),
				product.getCategory().name(), product.getUploadDate(), logoFromProduct(product), product.getCreator().getUserId()).build();
	}
		
	private void insertProducts(List<Product> products) {
		for(Product product : products)
			insertProduct(product);
	}
	
	private void assertEqualsReversedOrderedList(List<Product> expected, List<Product> actual) {
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++) {
			Product expectedProduct = expected.get(expected.size()-i-1);
			Product actualProduct = actual.get(i);
			assertEquals(expectedProduct, actualProduct);
			if (i > 0)
				assertTrue(actualProduct.getId() < actual.get(i-1).getId());
		}		
	}

	private void assertRetrievedCategory(Category category, List<Product> productList) {
		List<Product> productsByCategory = productDao.getPlainProductsByCategory(category.name());
		
		for (Product product : productsByCategory) {
			assertTrue(productList.contains(product));
			assertEquals(category, product.getCategory());
		}
		
		assertEquals(LIST_SIZE / Category.values().length, productsByCategory.size());
	}
}
