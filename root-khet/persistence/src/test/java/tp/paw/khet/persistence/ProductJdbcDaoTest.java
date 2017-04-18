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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ProductJdbcDaoTest {

	private static final int LIST_SIZE = 20;
	
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
		List<Product> expectedProducts = dummyProductList(LIST_SIZE, 0);
		insertProducts(expectedProducts, 0);
				
		List<Product> actualProducts = productDao.getProducts();
		
		assertEquals(expectedProducts.size(), actualProducts.size());
		
		for (int i = 0; i < expectedProducts.size(); i++) {
			Product expected = expectedProducts.get(expectedProducts.size()-i-1);
			Product actual = actualProducts.get(i);
			assertEquals(expected, actual);
			if (i > 0)
				assertTrue(actual.getUploadDate().compareTo(actualProducts.get(i-1).getUploadDate()) < 0);
		}
		
		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getProductsByCategoryTest() {
		Category[] categories = Category.values();
		List<Product> productList = dummyProductList(categories.length * 3, 0);
		insertProducts(productList, 0);
		
		for (int i = 0; i < categories.length; i++)
			assertRetrievedCategory(categories[i], productList);

		assertEquals(categories.length * 3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}

	private void assertRetrievedCategory(Category category, List<Product> productList) {
		List<Product> productsByCategory = productDao.getProductsByCategory(category.name());
		
		for (Product product : productsByCategory) {
			assertTrue(productList.contains(product));
			assertEquals(category, product.getCategory());
		}
		
		assertEquals(3, productsByCategory.size());
	}

	@Test
	public void getCreatorByProductIdTest() {
		User expected = dummyUser(0);
		insertProduct(dummyProduct(0), 0);
		
		User actual = productDao.getCreatorByProductId(0);
		
		assertEqualsUsers(expected, actual);
	}
	
	@Test
	public void createProductTest() {
		Product expected = dummyProduct(0);
		Product actual = insertProduct(expected, 0);
		
		assertEqualsProducts(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getProductByProductIdTest() {
		Product expected = dummyProduct(0);
		insertProduct(expected, 0);
		
		Product actual = productDao.getProductByProductId(0);
		
		assertEqualsProducts(expected, actual);
		assertNull(productDao.getProductByProductId(1));
	}
	
	@Test
	public void getLogoByProductIdTest() {
		Product dummyProduct = dummyProduct(0);
		insertProduct(dummyProduct, 0);
		
		byte[] logo = productDao.getLogoByProductId(0);
		
		assertArrayEquals(logo, logoFromProduct(dummyProduct));
	}
		
	private void insertDummyUser() {
		User dummy = dummyUser(0);
		userDao.createUser(dummy.getName(), dummy.getEmail());
	}
	
	private Product insertProduct(Product product, int creatorId) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), product.getWebsite(),
				product.getCategory().name(), product.getUploadDate(), logoFromProduct(product), creatorId);
	}
		
	private void insertProducts(List<Product> products, int creatorId) {
		for(Product product : products)
			insertProduct(product, creatorId);
	}	
}
