package tp.paw.khet.persistence;

import static org.junit.Assert.*;
import static tp.paw.khet.testutils.ProductTestUtils.dummyProduct;
import static tp.paw.khet.testutils.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.testutils.UserTestUtils.*;
import static tp.paw.khet.testutils.VideoTestUtils.*;

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
import tp.paw.khet.Video;
import tp.paw.khet.exception.DuplicateEmailException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class VideoJdbcDaoTest {

	private final static String TABLE_NAME = "videos";
	private final static int DUMMY_LIST_SIZE = 20;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private VideoJdbcDao videoDao;
		
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
		
		insertDummyUser();
		insertDummyProduct();
	}
	
	@Test
	public void createVideoTest() {
		Video expected = dummyVideo(0);
		
		Video actual = videoDao.createVideo(expected.getVideoId(), 0);
		
		assertEquals(expected, actual);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}
	
	@Test
	public void getVideosByProductIdTest() {
		List<Video> expectedList = dummyVideoList(DUMMY_LIST_SIZE, 0);
		insertVideoList(expectedList);
		
		List<Video> actualList = videoDao.getVideosByProductId(0);
		
		assertTrue(expectedList.containsAll(actualList));
		assertTrue(actualList.containsAll(expectedList));
		
		assertEquals(DUMMY_LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}
	
	@Test
	public void onDeleteCascadeTest() {
		Video dummy = dummyVideo(0);
		videoDao.createVideo(dummy.getVideoId(), 0);
		
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));

		JdbcTestUtils.deleteFromTables(jdbcTemplate, "products");
		
		assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}

	private void insertVideoList(List<Video> videoList) {
		for (Video video : videoList)
			videoDao.createVideo(video.getVideoId(), video.getProductId());
	}
	
	private void insertDummyProduct() {
		Product dummy = dummyProduct(0);
		productDao.createProduct(dummy.getName(), dummy.getDescription(), dummy.getShortDescription(), dummy.getWebsite(), dummy.getCategory().toString(),
				dummy.getUploadDate(), logoFromProduct(dummy), 0);
	}

	private void insertDummyUser() throws DuplicateEmailException {
		User dummy = dummyUser(0);
		userDao.createUser(dummy.getName(), dummy.getEmail(), dummy.getPassword(), profilePictureFromUser(dummy));
	}
}
