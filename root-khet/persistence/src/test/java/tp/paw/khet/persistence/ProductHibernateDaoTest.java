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
import static tp.paw.khet.model.UserTestUtils.dummyUserList;
import static tp.paw.khet.model.UserTestUtils.profilePictureFromUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductSortCriteria;
import tp.paw.khet.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
public class ProductHibernateDaoTest {

	private static final int CATEGORY_PRODUCT_EACH = 5;
	private static final int LIST_SIZE = Category.values().length * CATEGORY_PRODUCT_EACH;
	
	@Autowired
	private ProductHibernateDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	private List<User> dummyUserList;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
		dummyUserList = dummyUserList(LIST_SIZE, 1);
		insertDummyUsers(dummyUserList);
	}

	@Test
	public void createProductTest() {
		Product expected = dummyProduct(1);
		Product actual = insertProduct(expected);

		assertEqualsFullProducts(expected, actual);
	}
	
	@Test
	public void getPlainProductsRangePopularityTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		voteList(expected); // List is sorted by most popular products first

		testProductSortCriteria(Optional.empty(), ProductSortCriteria.POPULARITY, expected);
	}
		
	@Test
	public void getPlainProductsRangeCategoryPopularityTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		voteList(expected); // List is sorted by most popular products first

		for (Category category : Category.values()) {
			List<Product> filtered = new ArrayList<>(expected);
			filtered.removeIf(p -> !p.getCategory().equals(category));
			testProductSortCriteria(Optional.of(category), ProductSortCriteria.POPULARITY, filtered);
		}
	}
	
	private void voteList(List<Product> expected) {
		for (int i = 0; i < expected.size(); i++)
			voteProduct(expected.get(i), expected.size() - i);
	}

	private void voteProduct(Product product, int votes) {
		for (int i = 0; i < votes; i++) {
			product.getVotingUsers().add(dummyUserList.get(i));
			dummyUserList.get(i).getVotedProducts().add(product);
		}
	}
	
	private void testProductSortCriteria(Optional<Category> category, ProductSortCriteria sortCriteria, List<Product> expected) {
		List<Product> actual = category.isPresent() ? 
							   productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, 0, expected.size()) :
							   productDao.getPlainProductsRange(sortCriteria, 0, expected.size());
		
		assertEqualsList(expected, actual);
		
		List<Product> halfActual = category.isPresent() ? 
				productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, 0, expected.size()/2) :
				productDao.getPlainProductsRange(sortCriteria, 0, expected.size()/2);
		
		for (int i = 0; i < expected.size()/2; i++)
			assertEqualsPlainProducts(expected.get(i), halfActual.get(i));
		
		List<Product> halfActualOffset = category.isPresent() ?
				productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, expected.size()/2, expected.size()/2) :
				productDao.getPlainProductsRange(sortCriteria, expected.size()/2, expected.size()/2);
		
		for (int i = 0; i < expected.size()/2; i++)
			assertEqualsPlainProducts(expected.get(i + expected.size()/2), halfActualOffset.get(i));		
	}
	
	private void assertEqualsList(List<Product> expected, List<Product> actual) {
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsPlainProducts(expected.get(i), actual.get(i));
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

	private User insertDummyUser(User dummy) throws DuplicateEmailException {
		return userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(), profilePictureFromUser(dummy));
	}
	
	private void insertDummyUsers(List<User> dummyUsers) throws DuplicateEmailException {
		for (int i = 0; i < dummyUsers.size(); i++) {
			User u = dummyUsers.get(i);
			dummyUsers.set(i, insertDummyUser(u));
		}
	}
	
	private Product insertProduct(Product product) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), product.getWebsite(),
				product.getCategory(), product.getUploadDate(), product.getLogo(), product.getCreator());
	}
		
	private void insertProducts(List<Product> products) {
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			products.set(i, insertProduct(p));
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