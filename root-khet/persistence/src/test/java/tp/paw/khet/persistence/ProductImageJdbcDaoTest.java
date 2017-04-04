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

import tp.paw.khet.ProductImage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ProductImageJdbcDaoTest {

	private final static int ID = 0;
	private final static String TABLE_NAME = "productImages";
	private final static int DUMMY_LIST_SIZE = 20;
	
	@Autowired
	private ProductImageJdbcDao productImageDao;
	
	@Autowired
	private UserJdbcDao userDao;
	
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
		List<ProductImage> expectedList = dummyProductImageList(DUMMY_LIST_SIZE);
		
		for (ProductImage productImage : expectedList)
			productImageDao.createProductImage(productImage.getProductImageId(), productImage.getProductId(), productImage.getData());
		
		List<Integer> retrievedList = productImageDao.getImagesIdByProductId(ID);
		
		int i;
		for (i = 0; i < expectedList.size(); i++)
			assertEquals(expectedList.get(i).getProductImageId(), retrievedList.get(i).intValue());
		
		assertEquals(i, retrievedList.size());
		assertEquals(0, productImageDao.getImagesIdByProductId(ID+1).size());
	}
	
	@Test
	public void getImageByIdsTest() {
		ProductImage expected = dummyProductImage(0);
		
		productImageDao.createProductImage(expected.getProductImageId(), expected.getProductId(), expected.getData());
		
		ProductImage retrieved = productImageDao.getImageByIds(0, ID);
		
		assertIdenticalProductImages(expected, retrieved);
	}
	
	@Test
	public void createProductImageTest() {
		ProductImage expected = dummyProductImage(0);
		
		ProductImage created = productImageDao.createProductImage(expected.getProductImageId(), 
				expected.getProductId(), expected.getData());
		
		assertIdenticalProductImages(expected, created);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}

	private ProductImage dummyProductImage(int productImageId) {
		return new ProductImage(productImageId, ID, new byte[]{(byte) productImageId});
	}
	
	private List<ProductImage> dummyProductImageList(int size) {
		List<ProductImage> list = new ArrayList<ProductImage>(size);
		for (int i = 0; i < size; i++)
			list.add(dummyProductImage(i));
		
		return list;
	}
	
	private void insertDummyProduct() {
		productDao.createProduct("a", "b", "c", LocalDateTime.now(), new byte[]{0x20}, ID);
	}

	private void insertDummyUser() {
		userDao.createUser("a", "b");
	}

	private void assertIdenticalProductImages(ProductImage expected, ProductImage created) {
		assertEquals(expected, created);
		assertEquals(expected.getProductImageId(), created.getProductImageId());
		assertEquals(expected.getProductId(), created.getProductId());
		assertArrayEquals(expected.getData(), created.getData());
	}

}
