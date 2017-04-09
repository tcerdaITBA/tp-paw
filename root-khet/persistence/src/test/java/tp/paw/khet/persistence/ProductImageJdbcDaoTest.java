package tp.paw.khet.persistence;

import static org.junit.Assert.assertEquals;
import static tp.paw.khet.testutils.ProductImageTestUtils.*;
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
import tp.paw.khet.ProductImage;
import tp.paw.khet.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ProductImageJdbcDaoTest {

	private final static String TABLE_NAME = "productImages";
	private final static int DUMMY_LIST_SIZE = 20;
	
	@Autowired
	private ProductImageJdbcDao productImageDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
				
		insertDummyUser();
		insertDummyProduct();
	}

	@Test
	public void getImagesIdByProductIdTest() {
		List<ProductImage> expectedList = dummyProductImageList(DUMMY_LIST_SIZE, 0, 0);
		
		for (ProductImage productImage : expectedList)
			productImageDao.createProductImage(productImage.getProductImageId(), 0, productImage.getData());
		
		List<Integer> actualList = productImageDao.getImagesIdByProductId(0);
		
		int i;
		for (i = 0; i < expectedList.size(); i++)
			assertEquals(expectedList.get(i).getProductImageId(), actualList.get(i).intValue());
		
		assertEquals(i, actualList.size());
		assertEquals(0, productImageDao.getImagesIdByProductId(1).size());
		assertEquals(DUMMY_LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}
	
	@Test
	public void getImageByIdsTest() {
		ProductImage expected = dummyProductImage(0, 0);
		
		productImageDao.createProductImage(expected.getProductImageId(), expected.getProductId(), expected.getData());
		
		ProductImage actual = productImageDao.getImageByIds(0, 0);
		
		assertEqualsProductImages(expected, actual);
	}
	
	@Test
	public void createProductImageTest() {
		ProductImage expected = dummyProductImage(0, 0);
		
		ProductImage actual = productImageDao.createProductImage(expected.getProductImageId(), 
				expected.getProductId(), expected.getData());
		
		assertEqualsProductImages(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}
	
	private void insertDummyProduct() {
		Product dummy = dummyProduct(0);
		productDao.createProduct(dummy.getName(), dummy.getDescription(), dummy.getShortDescription(), 
				dummy.getUploadDate(), logoFromProduct(dummy), 0);
	}

	private void insertDummyUser() {
		User dummy = dummyUser(0);
		userDao.createUser(dummy.getName(), dummy.getMail());
	}
}
