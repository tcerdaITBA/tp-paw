package tp.paw.khet.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tp.paw.khet.model.ProductImageTestUtils.*;
import static tp.paw.khet.model.ProductTestUtils.dummyProductBuilder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductImage;
import tp.paw.khet.persistence.ProductImageDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductImageServiceImplTest {

	@Mock
	private ProductImageDao productImageDaoMock;

	@InjectMocks
	private ProductImageServiceImpl productImageService;

	@Test
	public void getImagesIdByProductIdTest() {
		List<Integer> expectedList = dummyProductImageIdList(20);
		when(productImageDaoMock.getImagesIdByProductId(0)).thenReturn(expectedList);

		List<Integer> actualList = productImageService.getImagesIdByProductId(0);

		assertEquals(expectedList.size(), actualList.size());

		for (int i = 0; i < expectedList.size(); i++)
			assertEquals(expectedList.get(i), actualList.get(i));

		assertTrue(productImageService.getImagesIdByProductId(1).isEmpty());
		verify(productImageDaoMock, times(2)).getImagesIdByProductId(anyInt());
	}

	@Test
	public void getImageByIdsTest() {
		ProductImage expected = dummyProductImage(0, 0);
		when(productImageDaoMock.getImageByIds(0, 0)).thenReturn(expected);

		ProductImage actual = productImageService.getImageByIds(0, 0);

		assertEqualsProductImages(expected, actual);
		assertNull(productImageService.getImageByIds(1, 0));
		assertNull(productImageService.getImageByIds(0, 1));
		verify(productImageDaoMock, times(3)).getImageByIds(anyInt(), anyInt());
	}

	@Test
	public void createProductImageTest() {
		ProductImage expected = dummyProductImage(0, 0);
		when(productImageDaoMock.createProductImage(0, 0, expected.getData())).thenReturn(expected);

		ProductImage actual = productImageService.createProductImage(0, 0, expected.getData());

		assertEqualsProductImages(expected, actual);
		verify(productImageDaoMock, times(1)).createProductImage(0, 0, expected.getData());
	}

	@Test
	public void getImagesIdsFromProduct() {
		List<ProductImage> images = dummyProductImageList(4, 0, 0);
		Product dummyProduct = dummyProductBuilder(0).images(images).build();

		int i = 0;
		for (Integer id : productImageService.getImagesIdsFromProduct(dummyProduct))
			assertEquals(Integer.valueOf(i++), id);
	}

	private List<Integer> dummyProductImageIdList(int size) {
		List<Integer> list = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++)
			list.add(i);

		return list;
	}
}
