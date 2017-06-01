package tp.paw.khet.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tp.paw.khet.model.ProductTestUtils.assertEqualsFullProducts;
import static tp.paw.khet.model.ProductTestUtils.assertEqualsPlainProducts;
import static tp.paw.khet.model.ProductTestUtils.dummyPlainProductList;
import static tp.paw.khet.model.ProductTestUtils.dummyPlainProductListWithCategory;
import static tp.paw.khet.model.ProductTestUtils.dummyPlainProductListWithUserId;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.persistence.ProductDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
	
	private static final int LIST_SIZE = 20;
	
	@Mock
	private ProductDao productDaoMock;

	@Mock
	private CommentService commentServiceMock;

	@Mock
	private VideoService videoServiceMock;
	
	@Mock
	private UserService userServiceMock;
	
	@InjectMocks
	private ProductServiceImpl productService;
	
	@Before
	public void setUp() {
		when(userServiceMock.getUserById(0)).thenReturn(dummyUser(0));
	}
	
	@Test
	public void getProductsTest() {
		List<Product> expected = dummyPlainProductList(LIST_SIZE, 0);
		when(productDaoMock.getPlainProducts()).thenReturn(expected);
		
		List<Product> actual = productService.getPlainProducts();
		
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsPlainProducts(expected.get(i), actual.get(i));
		
		verify(productDaoMock, times(1)).getPlainProducts();
	}

	@Test
	public void getLogoByProductIdTest() {
		Product dummyProduct = dummyProduct(0);
		byte[] expectedImage = logoFromProduct(dummyProduct);
		when(productDaoMock.getLogoByProductId(0)).thenReturn(expectedImage);
		
		byte[] actualImage = productService.getLogoByProductId(0);
		
		assertArrayEquals(expectedImage, actualImage);
		assertNull(productService.getLogoByProductId(1));
		verify(productDaoMock, times(2)).getLogoByProductId(anyInt());		
	}
	
	@Test
	public void getPlainProductsByUserIdTest() {
		List<Product> expected = dummyPlainProductListWithUserId(LIST_SIZE, 0, 0);
		when(productDaoMock.getPlainProductsByUserId(0)).thenReturn(expected);
		
		List<Product> actual = productService.getPlainProductsByUserId(0);
		
		assertEquals(expected.size(), actual.size());
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsPlainProducts(expected.get(i), actual.get(i));
		
		assertTrue(productDaoMock.getPlainProductsByUserId(1).isEmpty());

		verify(productDaoMock, times(2)).getPlainProductsByUserId(anyInt());
	}
	
	@Test
	public void getProductByIdTest() {
		Product expected = dummyProduct(0);
		when(productDaoMock.getFullProductById(0)).thenReturn(expected);
		when(commentServiceMock.getCommentsByProductId(0)).thenReturn(Collections.emptyList());
		
		Product actual = productService.getFullProductById(0);
		
		assertEqualsFullProducts(expected, actual);
		assertEquals(0, actual.getCommentFamilies().size());
		verify(productDaoMock, times(1)).getFullProductById(anyInt());
		
		assertNull(productService.getFullProductById(1));
	}
	
	@Test
	public void getProductByCategoryTest() {
		int id = 0;
		Category[] categories = Category.values();
		
		for (Category category : categories) {
			assertCategoryRetrieval(category, id);
			id += 5;
		}
		
		verify(productDaoMock, times(categories.length)).getPlainProductsByCategory(Matchers.any(Category.class));
	}
	
	private void assertCategoryRetrieval(Category category, int initialId) {
		List<Product> expected = dummyPlainProductListWithCategory(5, initialId, category);
		when(productDaoMock.getPlainProductsByCategory(category)).thenReturn(expected);
		
		List<Product> actual = productService.getPlainProductsByCategory(category);
		Collections.reverse(expected); // actual list is ordered descendant by upload date
		
		assertEquals(expected, actual);
		
		for (int i = 0; i < expected.size(); i++)
			assertEqualsPlainProducts(expected.get(0), actual.get(0));
	}
}
