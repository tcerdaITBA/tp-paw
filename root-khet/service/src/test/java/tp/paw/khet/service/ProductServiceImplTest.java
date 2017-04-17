package tp.paw.khet.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static tp.paw.khet.testutils.ProductTestUtils.*;
import static tp.paw.khet.testutils.UserTestUtils.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.Category;
import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.persistence.ProductDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
	
	@Mock
	private ProductDao productDaoMock;
	
	@InjectMocks
	private ProductServiceImpl productService;

	@Test
	public void testGetCreatorByProductId() {
		User expectedUser = dummyUser(0);
		when(productDaoMock.getCreatorByProductId(1)).thenReturn(expectedUser);
		
		User actualUser = productService.getCreatorByProductId(1);
		
		assertEqualsUsers(expectedUser, actualUser);
		assertNull(productService.getCreatorByProductId(2));
		verify(productDaoMock, times(2)).getCreatorByProductId(anyInt());		
	}
	
	@Test
	public void testGetProducts() {
		List<Product> expected = dummyProductList(20, 0);
		when(productDaoMock.getProducts()).thenReturn(expected);
		
		List<Product> actual = productService.getProducts();
		
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsProducts(expected.get(i), actual.get(i));
		
		verify(productDaoMock, times(1)).getProducts();
	}

	@Test
	public void getLogoByProductId() {
		Product dummyProduct = dummyProduct(0);
		byte[] expectedImage = logoFromProduct(dummyProduct);
		when(productDaoMock.getLogoByProductId(0)).thenReturn(expectedImage);
		
		byte[] actualImage = productService.getLogoByProductId(0);
		
		assertArrayEquals(expectedImage, actualImage);
		assertNull(productService.getLogoByProductId(1));
		verify(productDaoMock, times(2)).getLogoByProductId(anyInt());		
	}
	
	@Test
	public void getProductByIdTest() {
		Product expected = dummyProduct(0);
		when(productDaoMock.getProductByProductId(0)).thenReturn(expected);
		
		Product actual = productService.getProduct(0);
		
		assertEqualsProducts(expected, actual);
		assertNull(productService.getProduct(1));
		verify(productDaoMock, times(2)).getProductByProductId(anyInt());
	}
	
	@Test
	public void getProductByCategoryTest() {
		int id = 0;
		Category[] categories = Category.values();
		
		for (Category category : categories) {
			assertCategoryRetrieval(category, id);
			id += 5;
		}
		
		verify(productDaoMock, times(categories.length)).getProductsByCategory(anyString());
	}
	
	private void assertCategoryRetrieval(Category category, int initialId) {
		List<Product> expected = dummyProductListWithCategory(5, initialId, category);
		when(productDaoMock.getProductsByCategory(category.name())).thenReturn(expected);
		
		List<Product> actual = productService.getProductsByCategory(category);
		Collections.reverse(expected); // actual list is ordered descendant by upload date
		
		assertEquals(expected, actual);
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsProducts(expected.get(0), actual.get(0));
	}

}
