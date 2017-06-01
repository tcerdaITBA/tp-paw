package tp.paw.khet.persistence;

import static org.junit.Assert.assertEquals;
import static tp.paw.khet.model.ProductImageTestUtils.assertEqualsProductImages;
import static tp.paw.khet.model.ProductImageTestUtils.dummyProductImage;
import static tp.paw.khet.model.ProductImageTestUtils.dummyProductImageList;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;
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
import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductImage;
import tp.paw.khet.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
public class ProductImageHibernateDaoTest {

	private final static String TABLE_NAME = "productImages";
	private final static int DUMMY_LIST_SIZE = 20;
	
	@Autowired
	private ProductImageHibernateDao productImageDao;
	
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
		List<ProductImage> expectedList = dummyProductImageList(DUMMY_LIST_SIZE, 1, 1);
		
		for (ProductImage productImage : expectedList)
			productImageDao.createProductImage(productImage.getProductImageId(), 1, productImage.getData());
		
		List<Integer> actualList = productImageDao.getImagesIdByProductId(1);
		
		int i;
		for (i = 0; i < expectedList.size(); i++)
			assertEquals(expectedList.get(i).getProductImageId(), actualList.get(i).intValue());
		
		assertEquals(i, actualList.size());
		assertEquals(0, productImageDao.getImagesIdByProductId(2).size());
		assertEquals(DUMMY_LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}
	
	@Test
	public void getImageByIdsTest() {
		ProductImage expected = dummyProductImage(1, 1);
		
		productImageDao.createProductImage(expected.getProductImageId(), expected.getProductId(), expected.getData());
		
		ProductImage actual = productImageDao.getImageByIds(1, 1);
		
		assertEqualsProductImages(expected, actual);
	}
	
	@Test
	public void createProductImageTest() {
		ProductImage expected = dummyProductImage(1, 1);
		
		ProductImage actual = productImageDao.createProductImage(1, 1, expected.getData());
		
		assertEqualsProductImages(expected, actual);
	}
	
	private void insertDummyProduct() {
		Product dummy = dummyProduct(1);
		productDao.createProduct(dummy.getName(), dummy.getDescription(), dummy.getShortDescription(), dummy.getWebsite(), dummy.getCategory(),
				dummy.getUploadDate(), logoFromProduct(dummy), dummyUser(1));
	}

	private void insertDummyUser() throws DuplicateEmailException {
		User dummy = dummyUser(1);
		userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(), profilePictureFromUser(dummy));
	}
}
