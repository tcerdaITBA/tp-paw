package tp.paw.khet.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tp.paw.khet.model.ProductTestUtils.assertEqualsFullProducts;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.ProductTestUtils.logoFromProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.persistence.ProductDao;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

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
	public void getMaxProductPageWithSizeTest() {
		assertMaxPageSize(50, 9, 6); // 5 pages of size 9 and 1 page of size 5
		assertMaxPageSize(50, 10, 5); // 5 pages of size 10

		verify(productDaoMock, times(2)).getTotalProducts();
		verify(productDaoMock, times(0)).getTotalProductsInCategory(any());
	}

	private void assertMaxPageSize(int totalProducts, int pageSize, int expected) {
		when(productDaoMock.getTotalProducts()).thenReturn(totalProducts);

		int actual = productService.getMaxProductPageWithSize(Optional.empty(), pageSize);

		assertEquals(expected, actual);
	}

	@Test
	public void getMaxProductPageCategoryWithSizeTest() {
		assertMaxPageCategorySize(50, 9, 6); // 5 pages of size 9 and 1 page of
												// size 5
		assertMaxPageCategorySize(50, 10, 5); // 5 pages of size 10

		verify(productDaoMock, times(2)).getTotalProductsInCategory(any());
		verify(productDaoMock, times(0)).getTotalProducts();
	}

	private void assertMaxPageCategorySize(int totalProducts, int pageSize, int expected) {
		when(productDaoMock.getTotalProductsInCategory(any())).thenReturn(totalProducts);

		int actual = productService.getMaxProductPageWithSize(Optional.of(Category.APP), pageSize);

		assertEquals(expected, actual);
	}
}
