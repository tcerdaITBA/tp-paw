package tp.paw.khet.service;

import static org.junit.Assert.*;
import static tp.paw.khet.model.FavListTestUtils.dummyFavList;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.persistence.FavListDao;

@RunWith(MockitoJUnitRunner.class)
public class FavListServiceImplTest {

	@Mock
	private ProductService productServiceMock;
	
	@Mock
	private UserService userServiceMock;
	
	@Mock
	private FavListDao favListDaoMock;
	
	@InjectMocks
	private FavListServiceImpl favListService;
	
	private User dummyUser;
	private Product dummyProduct;
	private FavList dummyFavList;
	
	@Before
	public void setUp() {
		dummyUser = dummyUser(0);
		dummyProduct = dummyProduct(0);
		dummyFavList = dummyFavList(0, dummyUser);
		
		Mockito.when(productServiceMock.getPlainProductById(0)).thenReturn(dummyProduct);
		Mockito.when(userServiceMock.getUserById(0)).thenReturn(dummyUser);
		Mockito.when(favListDaoMock.getFavListById(0)).thenReturn(dummyFavList);
		Mockito.when(favListDaoMock.getFavListByIdWithCreator(0)).thenReturn(dummyFavList);
	}

	@Test
	public void createFavListTest() throws DuplicateFavListException {
		final FavList expected = dummyFavList;
		final Set<FavList> favLists = dummyUser.getFavLists();
		
		assertTrue(favLists.isEmpty());
		
		final FavList actual = favListService.createFavList(expected.getName(), expected.getId());
		
		assertEquals(expected, actual);
		assertEquals(1, favLists.size());
		assertTrue(favLists.contains(actual));
	}

	@Test
	public void deleteFavListTest() {
		final Set<FavList> favLists = dummyUser.getFavLists();
		dummyUser.addFavList(dummyFavList);
		
		assertFalse(favListService.deleteFavList(dummyFavList.getId() + 1));  // deleting unexistent favList
		assertEquals(1, favLists.size());
		
		assertTrue(favListService.deleteFavList(dummyFavList.getId()));
		assertTrue(favLists.isEmpty());
	}
	
	@Test
	public void getFavListByIdTest() {
		assertEquals(dummyFavList, favListService.getFavListById(dummyFavList.getId()));
	}
	
	@Test
	public void getFavListByIdWithCreatorTest() {
		assertEquals(dummyFavList, favListService.getFavListByIdWithCreator(dummyFavList.getId()));
	}
	
	@Test
	public void addProductToFavListTest() {
		final Set<Product> productList = dummyFavList.getProductList();
		assertTrue(productList.isEmpty());
		
		favListService.addProductToFavList(dummyFavList.getId(), dummyProduct.getId());
		
		assertEquals(1, productList.size());
		assertTrue(productList.contains(dummyProduct));
	}

	@Test
	public void removeProductFromFavListTest() {
		final Set<Product> productList = dummyFavList.getProductList();
		dummyFavList.addProduct(dummyProduct);
		
		favListService.removeProductFromFavList(dummyFavList.getId(), dummyProduct.getId());
		
		assertTrue(productList.isEmpty());
	}
}
