package tp.paw.khet.persistence;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static tp.paw.khet.model.ProductTestUtils.assertEqualsFullProducts;
import static tp.paw.khet.model.ProductTestUtils.assertEqualsPlainProducts;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.ProductTestUtils.dummyProductList;
import static tp.paw.khet.model.ProductTestUtils.dummyProductListWithUserId;
import static tp.paw.khet.model.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;
import static tp.paw.khet.model.UserTestUtils.dummyUserList;
import static tp.paw.khet.model.UserTestUtils.profilePictureFromUser;

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
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
public class ProductHibernateDaoTest {

	private static final int LIST_SIZE = Category.values().length * 5;
	
	@Autowired
	private ProductHibernateDao productDao;
	
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

//	@Test
//	public void getProductsTest() {
//		List<Product> expected = dummyProductList(LIST_SIZE, 1);
//		insertProducts(expected);
//		
//		List<Product> actual = productDao.getPlainProducts();
//		
//		assertEqualsReversedSortedList(expected, actual);
//		
//		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
//	}
	
//	@Test
//	public void getProductsByCategoryTest() {
//		Category[] categories = Category.values();
//		List<Product> productList = dummyProductList(LIST_SIZE, 1);
//		insertProducts(productList);
//		
//		for (int i = 0; i < categories.length; i++)
//			assertRetrievedCategory(categories[i], productList);
//
//		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
//	}

	@Test
	public void createProductTest() {
		Product expected = dummyProduct(1);
		Product actual = insertProduct(expected);

		assertEqualsFullProducts(expected, actual);
	}
	
	@Test
	public void getProductByProductIdTest() {
		Product expected = dummyProduct(1);
		insertProduct(expected);
		
		Product actual = productDao.getFullProductById(1);
		
		assertEqualsFullProducts(expected, actual);
		assertNull(productDao.getFullProductById(2));
		
		assertEqualsPlainProducts(expected, productDao.getPlainProductById(1));
	}
	
	@Test
	public void getPlainProductsByUserIdTest() {
		List<Product> expected = dummyProductListWithUserId(LIST_SIZE, 1, 1);
		insertProducts(expected);
		
		List<Product> actual = productDao.getPlainProductsByUserId(1);
		
		assertEqualsReversedSortedList(expected, actual);		
	}

	@Test
	public void getLogoByProductIdTest() {
		Product dummyProduct = dummyProduct(1);
		insertProduct(dummyProduct);
		
		byte[] logo = productDao.getLogoByProductId(1);
		
		assertArrayEquals(logo, logoFromProduct(dummyProduct));
	}
	
	@Test
	public void deleteProductByUserId() {
		Product dummyProduct = dummyProduct(1);
		insertProduct(dummyProduct);

		assertTrue(productDao.deleteProductById(1));
		assertFalse(productDao.deleteProductById(1));
	}
	
//	@Test
//	public void getPlainProductsRangePopularityTest() {
//		List<Product> expected = dummyProductList(LIST_SIZE, 1);
//		insertProducts(expected);
//		voteList(expected); // List is sorted by most popular products first
//				
//		List<Product> actual = productDao.getPlainProductsRangePopularity(0, LIST_SIZE);
//		
//		assertEqualsList(expected, actual);
//	}
//	
//	private void voteList(List<Product> expected) {
//		for (int i = 0; i < expected.size(); i++)
//			voteProduct(expected.get(i), expected.size() - i);
//	}
//
//	private void voteProduct(Product product, int votes) {
//		for (int i = 0; i < votes; i++)
//			product.getVotingUsers().add(dummyUser(i));
//	}

	private void assertEqualsList(List<Product> expected, List<Product> actual) {
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsPlainProducts(expected.get(i), actual.get(i));
	}
	
	@Test
	public void getPlainProductsByKeywordMatchNameTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		String keyword = expected.get(0).getName().substring(0, 3);
		String noMatchKeyword = expected.get(0).getName().substring(1, 3);

		assertSearch(keyword, noMatchKeyword, expected);
	}
	
	@Test
	public void getPlainProductsByKeywordMatchTaglineTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		String keyword = expected.get(0).getShortDescription().substring(0, 3);
		String noMatchKeyword = expected.get(0).getShortDescription().substring(1, 3);

		assertSearch(keyword, noMatchKeyword, expected);
		List<Product> actual = productDao.getPlainProductsByKeyword("desc", LIST_SIZE);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
	}
	
	private void assertSearch(String keyword, String noMatchKeyword, List<? extends Product> expected) {
		List<Product> actual = productDao.getPlainProductsByKeyword(keyword, LIST_SIZE);
		
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		
		assertSortedByName(actual);
		
		expected = actual.subList(0, 5);
		actual = productDao.getPlainProductsByKeyword(keyword, 5);
				
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
		
		assertTrue(productDao.getPlainProductsByKeyword("sucutrule", LIST_SIZE).isEmpty());
				
		assertTrue(productDao.getPlainProductsByKeyword(noMatchKeyword, LIST_SIZE).isEmpty());
		assertEqualsPlainProducts(dummyProduct(1), productDao.getPlainProductsByKeyword("1", LIST_SIZE).get(0));
	}
	
	private void assertSortedByName(List<? extends Product> actual) {
		for (int i = 1; i < actual.size(); i++) {
			String shouldBeLower = actual.get(i-1).getName();
			String shouldBeHiger = actual.get(i).getName();
			assertTrue(shouldBeLower.compareToIgnoreCase(shouldBeHiger) < 0);
		}
	}

	private void insertDummyUser() throws DuplicateEmailException {
		List<User> list = dummyUserList(LIST_SIZE, 1);
		for (User dummy : list)
			userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(), profilePictureFromUser(dummy));
	}
	
	private Product insertProduct(Product product) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), product.getWebsite(),
				product.getCategory(), product.getUploadDate(), product.getLogo(), product.getCreator());
	}
		
	private void insertProducts(List<Product> products) {
		for(Product product : products)
			insertProduct(product);
	}

	private void assertEqualsReversedSortedList(List<Product> expected, List<Product> actual) {
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++) {
			Product expectedProduct = expected.get(expected.size()-i-1);
			Product actualProduct = actual.get(i);
			assertEqualsFullProducts(expectedProduct, actualProduct);
			if (i > 0)
				assertTrue(actualProduct.getId() < actual.get(i-1).getId());
		}		
	}

//	private void assertRetrievedCategory(Category category, List<Product> productList) {
//		List<Product> productsByCategory = productDao.getPlainProductsByCategory(category);
//		
//		for (Product product : productsByCategory) {
//			assertTrue(productList.contains(product));
//			assertEquals(category, product.getCategory());
//		}
//		
//		assertEquals(LIST_SIZE / Category.values().length, productsByCategory.size());
//	}
}