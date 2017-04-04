package tp.paw.khet.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tp.paw.khet.ProductImage;
import tp.paw.khet.persistence.ProductImageDao;

public class ProductImageServiceImplTest {

	private final static int ID = 1;
	private final static int DUMMY_LIST_SIZE = 20;
	
	private ProductImageServiceImpl productImageService;
	private ProductImageDao productImageDaoMock;
	
	@Before
	public void setUp() throws Exception {
		productImageDaoMock = Mockito.mock(ProductImageDao.class);
		productImageService = new ProductImageServiceImpl(productImageDaoMock);
	}

	@Test
	public void getImagesIdByProductIdTest() {
		List<Integer> expectedIdList = dummyProductImageIdList(DUMMY_LIST_SIZE);
		Mockito.when(productImageDaoMock.getImagesIdByProductId(ID)).thenReturn(expectedIdList);
		
		List<Integer> retrievedList = productImageService.getImagesIdByProductId(ID);
		
		int i;
		for (i = 0; i < expectedIdList.size(); i++)
			assertEquals(expectedIdList.get(i), retrievedList.get(i));
		
		assertEquals(i, retrievedList.size());
	}
	
	@Test
	public void getImageByIdsTest() {
		ProductImage expected = dummyProductImage(ID);
		Mockito.when(productImageDaoMock.getImageByIds(expected.getProductImageId(), expected.getProductId())).thenReturn(expected);
		
		ProductImage retrieved = productImageDaoMock.getImageByIds(expected.getProductImageId(), expected.getProductId());
		
		assertIdenticalProductImages(expected, retrieved);
	}

	@Test
	public void createProductImageTest() {
		ProductImage expected = dummyProductImage(ID);
		Mockito.when(productImageDaoMock.createProductImage(expected.getProductImageId(), 
				expected.getProductId(), expected.getData())).thenReturn(expected);
		
		ProductImage retrieved = productImageService.createProductImage(expected.getProductImageId(), expected.getProductId(), expected.getData());
		
		assertIdenticalProductImages(expected, retrieved);
	}
	
	private ProductImage dummyProductImage(int productImageId) {
		return new ProductImage(productImageId, ID, new byte[]{(byte) productImageId});
	}
	
	private List<Integer> dummyProductImageIdList(int size) {
		List<Integer> list = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++)
			list.add(i);
		
		return list;
	}
	
	private void assertIdenticalProductImages(ProductImage expected, ProductImage retrieved) {
		assertEquals(expected, retrieved);
		assertEquals(expected.getProductImageId(), retrieved.getProductImageId());
		assertEquals(expected.getProductId(), retrieved.getProductId());
		assertArrayEquals(expected.getData(), retrieved.getData());
	}
	
	
}
