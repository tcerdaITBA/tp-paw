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
	}

	@Test
	public void getProductsTest() {
		insertDummyUser();
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
	public void getCreatorByProductIdTest() {
		insertDummyUser();
		User expected = dummyUser(0);
		insertProduct(dummyProduct(0), 0);
		
		User actual = productDao.getCreatorByProductId(0);
		
		assertEqualsUsers(expected, actual);
	}
	
	@Test
	public void createProductTest() {
		insertDummyUser();
		Product expected = dummyProduct(0);
		Product actual = insertProduct(expected, 0);
		
		assertEqualsProducts(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getLogoByProductIdTest() {
		insertDummyUser();
		Product dummyProduct = dummyProduct(0);
		insertProduct(dummyProduct, 0);
		
		byte[] logo = productDao.getLogoByProductId(0);
		
		assertArrayEquals(logo, logoFromProduct(dummyProduct));
	}
		
	private void insertDummyUser() {
		User dummy = dummyUser(0);
		userDao.createUser(dummy.getName(), dummy.getMail());
	}
	
	private Product insertProduct(Product product, int creatorId) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), 
				product.getUploadDate(), logoFromProduct(product), creatorId);
	}
		
	private void insertProducts(List<Product> products, int creatorId) {
		for(Product product : products)
			insertProduct(product, creatorId);
	}	
}
