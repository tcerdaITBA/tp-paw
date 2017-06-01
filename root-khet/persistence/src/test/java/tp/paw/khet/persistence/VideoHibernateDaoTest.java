package tp.paw.khet.persistence;

import static org.junit.Assert.*;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.model.UserTestUtils.*;
import static tp.paw.khet.model.VideoTestUtils.*;

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
import tp.paw.khet.model.User;
import tp.paw.khet.model.Video;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:schema.sql")
public class VideoHibernateDaoTest {

	private final static String TABLE_NAME = "videos";
	private final static int DUMMY_LIST_SIZE = 20;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private VideoHibernateDao videoDao;
		
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
		Video expected = dummyVideo(1);
		
		Video actual = videoDao.createVideo(expected.getVideoId(), 1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getVideosByProductIdTest() {
		List<Video> expectedList = dummyVideoList(DUMMY_LIST_SIZE, 1);
		insertVideoList(expectedList);
		
		List<Video> actualList = videoDao.getVideosByProductId(1);
		
		assertTrue(expectedList.containsAll(actualList));
		assertTrue(actualList.containsAll(expectedList));
		
		assertEquals(DUMMY_LIST_SIZE, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_NAME));
	}

	private void insertVideoList(List<Video> videoList) {
		for (Video video : videoList)
			videoDao.createVideo(video.getVideoId(), video.getProductId());
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
