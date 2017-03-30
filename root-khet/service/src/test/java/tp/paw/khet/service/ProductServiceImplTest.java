package tp.paw.khet.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.persistence.ProductDao;

public class ProductServiceImplTest {

	private static final int NUMBER_PRODUCTS = 10;
	
	private ProductDao productDao;
	private ProductServiceImpl productService;
	
	@Before
	public void setUp() throws Exception {
		productService = new ProductServiceImpl();
		productDao = Mockito.mock(ProductDao.class);
		productService.setProductDao(productDao);
	}

	@Test
	public void testGetProducts() {
		List<Product> expectedProducts = generateProducts();
		Mockito.when(productDao.getProducts()).thenReturn(expectedProducts);
		
		List<Product> retrievedProducts = productService.getProducts();
		assertEquals(expectedProducts, retrievedProducts);
	}
	
	@Test
	public void testGetUserByProduct() {
		int productId = 1;
		Product product = dummyProduct(productId);
		User expectedUser = new User("tomas", "cerda");
		Mockito.when(productDao.getCreatorByProductId(productId)).thenReturn(expectedUser);
		
		User retrievedUser = productService.getProductCreator(product);
		assertEquals(expectedUser, retrievedUser);
		
		Mockito.verify(productDao, Mockito.calls(1));
	}

	private List<Product> generateProducts() {
		List<Product> productList = new ArrayList<Product>(NUMBER_PRODUCTS);
		for (int i = 1; i <= NUMBER_PRODUCTS; i++)
			productList.add(dummyProduct(i));
		return productList;
	}

	private Product dummyProduct(int id) {
		return new Product(id, "", "", "", new Date());
	}
}
