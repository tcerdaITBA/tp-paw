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
	public void getImagesByProductIdTest() {
		List<ProductImage> expectedList = dummyProductImageList(DUMMY_LIST_SIZE);
		Mockito.when(productImageDaoMock.getImagesByProductId(ID)).thenReturn(expectedList);
		
		List<ProductImage> retrievedList = productImageService.getImagesByProductId(ID);
		
		int i;
		for (i = 0; i < expectedList.size(); i++)
			assertIdenticalProductImages(expectedList.get(i), retrievedList.get(i));
		
		assertEquals(i, retrievedList.size());
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
	
	private List<ProductImage> dummyProductImageList(int size) {
		List<ProductImage> list = new ArrayList<ProductImage>(size);
		for (int i = 0; i < size; i++)
			list.add(dummyProductImage(i));
		
		return list;
	}
	
	private void assertIdenticalProductImages(ProductImage expected, ProductImage created) {
		assertEquals(expected, created);
		assertEquals(expected.getProductImageId(), created.getProductImageId());
		assertEquals(expected.getProductId(), created.getProductId());
		assertArrayEquals(expected.getData(), created.getData());
	}
	
	
}
