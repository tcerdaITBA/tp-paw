package tp.paw.khet.persistence;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
		List<Product> expectedProducts = dummyProductList(LIST_SIZE);
		insertProducts(expectedProducts, 0);
		
		List<Product> retrievedProducts = productDao.getProducts();
		
		assertEquals(LIST_SIZE, retrievedProducts.size());
		assertFalse(retrievedProducts.isEmpty());
		
		for (int i = 0; i < expectedProducts.size(); i++) {
			Product expected = expectedProducts.get(expectedProducts.size()-i-1);
			Product retrieved = retrievedProducts.get(i);
			assertEquals(expected, retrieved);
			if (i > 0)
				assertTrue(retrieved.getUploadDate().compareTo(retrievedProducts.get(i-1).getUploadDate()) < 0);
		}
		
		assertEquals(LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}

	@Test
	public void getCreatorByProductIdTest() {
		insertDummyUser();
		User expectedUser = dummyUser();
		insertProduct(dummyProduct(0), 0);
		
		User retrievedUser = productDao.getCreatorByProductId(0);
		
		assertNotNull(retrievedUser);
		assertEquals(expectedUser, retrievedUser);
		assertEquals(expectedUser.getName(), retrievedUser.getName());
		assertEquals(expectedUser.getMail(), retrievedUser.getMail());
		assertEquals(expectedUser.getUserId(), retrievedUser.getUserId());
	}
	
	@Test
	public void createProductTest() {
		insertDummyUser(); // para que cumpla FK
		Product expectedProduct = dummyProduct(0);
		Product createdProduct = insertProduct(expectedProduct, 0);
		
		assertNotNull(createdProduct);
		
		assertEquals(0, createdProduct.getId());
		assertEquals(expectedProduct, createdProduct);
		assertEquals(expectedProduct.getName(), createdProduct.getName());
		assertEquals(expectedProduct.getDescription(), createdProduct.getDescription());
		assertEquals(expectedProduct.getShortDescription(), createdProduct.getShortDescription());
		assertEquals(expectedProduct.getUploadDate(), createdProduct.getUploadDate());
		
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
	
	@Test
	public void getLogoByProductIdTest() {
		Product dummyProduct = dummyProduct(0);
		insertDummyUser();
		insertProduct(dummyProduct, 0);
		
		byte[] logo = productDao.getLogoByProductId(0);
		
		assertArrayEquals(logo, imageFromProduct(dummyProduct));
	}
		
	private void insertDummyUser() {
		User dummy = dummyUser();
		userDao.createUser(dummy.getName(), dummy.getMail());
	}

	private User dummyUser() {
		return new User(0, "Tomás Cerdá", "tcerda@itba.edu.ar");
	}

	private Product dummyProduct(int id) {
		return new Product(id, "Product Seeker " + id, "Search a product " + id, "Seek products " + id, LocalDateTime.now().plusSeconds(id));
	}
	
	private Product insertProduct(Product product, int creatorId) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), 
				product.getUploadDate(), imageFromProduct(product), creatorId);
	}
	
	private List<Product> dummyProductList(int length) {
		List<Product> list = new ArrayList<Product>(length);
		
		for (int i = 0; i < length; i++)
			list.add(dummyProduct(i));
		
		return list;
	}
	
	private void insertProducts(List<Product> products, int creatorId) {
		for(Product product : products)
			productDao.createProduct(product.getName(), product.getDescription(), 
					product.getShortDescription(), product.getUploadDate(), imageFromProduct(product), creatorId);
	}
	
	private byte[] imageFromProduct(Product product) {
		return product.getName().getBytes();
	}
}
