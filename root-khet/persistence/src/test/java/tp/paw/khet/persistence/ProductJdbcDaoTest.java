package tp.paw.khet.persistence;

import static org.junit.Assert.*;

import java.time.LocalDate;
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

	@Autowired
	private ProductJdbcDao productDao;
	
	@Autowired
	private UserJdbcDao userDao;
	
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
		List<Product> expectedProducts = dummyProductList(20);
		insertProducts(expectedProducts, 0);
		
		List<Product> retrievedProducts = productDao.getProducts();
		
		assertEquals(20, retrievedProducts.size());
		assertFalse(retrievedProducts.isEmpty());
		
		assertTrue(expectedProducts.containsAll(retrievedProducts));
		assertTrue(retrievedProducts.containsAll(expectedProducts));
		
		assertEquals(20, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
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
		assertArrayEquals(expectedProduct.getLogo(), createdProduct.getLogo());
		assertEquals(expectedProduct.getUploadDate(), createdProduct.getUploadDate());

		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
	}
		
	private void insertDummyUser() {
		User dummy = dummyUser();
		userDao.createUser(dummy.getName(), dummy.getMail());
	}

	private User dummyUser() {
		return new User(0, "Tomás Cerdá", "tcerda@itba.edu.ar");
	}

	private Product dummyProduct(int id) {
		return new Product(id, "Product Seeker", "Search a product", "Seek products", LocalDate.now(), new byte[] {(byte) 0x80});
	}
	
	private Product insertProduct(Product product, int creatorId) {
		return productDao.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), 
				product.getUploadDate(), product.getLogo(), creatorId);
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
					product.getShortDescription(), product.getUploadDate(), product.getLogo(), creatorId);
	}
}
