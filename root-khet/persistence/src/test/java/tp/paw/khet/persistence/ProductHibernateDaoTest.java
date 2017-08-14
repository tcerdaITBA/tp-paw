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
import static tp.paw.khet.model.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUserList;
import static tp.paw.khet.model.UserTestUtils.profilePictureFromUser;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import tp.paw.khet.model.OrderCriteria;
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

	@Test // no category filter
	public void getPlainProductsRangeTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		voteList(expected);

		for (ProductSortCriteria psc : ProductSortCriteria.values())
			for (OrderCriteria orderCriteria : OrderCriteria.values())
				testProductSortCriteria(Optional.empty(), psc, orderCriteria, expected);
	}

	@Test // with category filter
	public void getPlainProductsRangeCategoryPopularityTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		voteList(expected);

		for (Category category : Category.values())
			for (ProductSortCriteria psc : ProductSortCriteria.values())
				for (OrderCriteria orderCriteria : OrderCriteria.values())
					testProductSortCriteria(Optional.of(category), psc, orderCriteria, expected);
	}

	private void voteList(List<Product> expected) {
		for (int i = 0; i < expected.size(); i++)
			voteProduct(expected.get(i), expected.size() - i);
	}

	private void voteProduct(Product product, int votes) {
		for (int i = 0; i < votes; i++) {
			product.addVoter(dummyUserList.get(i));
			dummyUserList.get(i).voteProduct(product);
		}
	}

	private void testProductSortCriteria(Optional<Category> category, ProductSortCriteria sortCriteria, OrderCriteria orderCriteria, List<Product> expected) {
		expected.sort(sortCriteria.getComparator(orderCriteria));

		if (category.isPresent())
			expected = expected.stream().filter(p -> p.getCategory().equals(category.get())).collect(Collectors.toList());

		List<Product> actual = category.isPresent()
				? productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, orderCriteria, 0, expected.size())
				: productDao.getPlainProductsRange(sortCriteria, orderCriteria, 0, expected.size());

		assertEqualsList(expected, actual);

		List<Product> halfActual = category.isPresent() ? 
				productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, orderCriteria, 0, expected.size() / 2)
				: productDao.getPlainProductsRange(sortCriteria, orderCriteria, 0, expected.size() / 2);

		for (int i = 0; i < expected.size() / 2; i++)
			assertEqualsPlainProducts(expected.get(i), halfActual.get(i));

		List<Product> halfActualOffset = category.isPresent() ? 
				productDao.getPlainProductsRangeByCategory(category.get(), sortCriteria, orderCriteria, expected.size() / 2, expected.size() / 2)
				: productDao.getPlainProductsRange(sortCriteria, orderCriteria, expected.size() / 2, expected.size() / 2);

		for (int i = 0; i < expected.size() / 2; i++)
			assertEqualsPlainProducts(expected.get(i + expected.size() / 2), halfActualOffset.get(i));
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
	public void getLogoByProductIdTest() {
		Product dummyProduct = dummyProduct(1);
		insertProduct(dummyProduct);

		byte[] logo = productDao.getLogoByProductId(1);

		assertArrayEquals(logo, logoFromProduct(dummyProduct));
	}

	@Test
	public void deleteProductById() {
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
	public void getTotalProductsTest() {
		assertEquals(0, productDao.getTotalProducts());

		insertProduct(dummyProduct(1));
		assertEquals(1, productDao.getTotalProducts());

		productDao.deleteProductById(1);
		assertEquals(0, productDao.getTotalProducts());
	}

	@Test
	public void getPlainProductsByKeywordMatchTaglineTest() {
		List<Product> expected = dummyProductList(LIST_SIZE, 1);
		insertProducts(expected);
		String keyword = expected.get(0).getShortDescription().substring(0, 3);
		String noMatchKeyword = expected.get(0).getShortDescription().substring(1, 3);

		assertSearch(keyword, noMatchKeyword, expected);
		List<Product> actual = productDao.getPlainProductsByKeyword(stringToSet("desc"), 0, LIST_SIZE);
		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));
	}

	private Set<String> stringToSet(final String str) {
		final String[] keywords = str.trim().split(" ");
		final Set<String> validKeywords = new HashSet<>();

		for (final String word : keywords)
			validKeywords.add(word);

		return validKeywords;
	}

	private void assertSearch(String keyword, String noMatchKeyword, List<? extends Product> expected) {
		List<Product> actual = productDao.getPlainProductsByKeyword(stringToSet(keyword), 0, LIST_SIZE);

		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));

		assertSortedByName(actual);

		expected = actual.subList(0, 5);
		actual = productDao.getPlainProductsByKeyword(stringToSet(keyword), 0, 5);

		assertTrue(expected.containsAll(actual));
		assertTrue(actual.containsAll(expected));

		assertTrue(productDao.getPlainProductsByKeyword(stringToSet("sucutrule"), 0, LIST_SIZE).isEmpty());

		assertTrue(productDao.getPlainProductsByKeyword(stringToSet(noMatchKeyword), 0, LIST_SIZE).isEmpty());
		assertEqualsPlainProducts(dummyProduct(1),
				productDao.getPlainProductsByKeyword(stringToSet("1"), 0, LIST_SIZE).get(0));
	}

	private void assertSortedByName(List<? extends Product> actual) {
		for (int i = 1; i < actual.size(); i++) {
			String shouldBeLower = actual.get(i - 1).getName();
			String shouldBeHiger = actual.get(i).getName();
			assertTrue(shouldBeLower.compareToIgnoreCase(shouldBeHiger) < 0);
		}
	}

	private User insertDummyUser(User dummy) throws DuplicateEmailException {
		return userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(),
				profilePictureFromUser(dummy));
	}

	private void insertDummyUsers(List<User> dummyUsers) throws DuplicateEmailException {
		for (int i = 0; i < dummyUsers.size(); i++) {
			User u = dummyUsers.get(i);
			dummyUsers.set(i, insertDummyUser(u));
		}
	}

	private Product insertProduct(Product product) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(),
				product.getWebsite(), product.getCategory(), product.getUploadDate(), product.getLogo(),
				product.getCreator());
	}

	private void insertProducts(List<Product> products) {
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			products.set(i, insertProduct(p));
		}
	}
}