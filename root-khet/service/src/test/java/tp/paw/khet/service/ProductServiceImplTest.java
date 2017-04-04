package tp.paw.khet.service;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.persistence.ProductDao;

public class ProductServiceImplTest {

	private static final int NUMBER_PRODUCTS = 10;
	
	private ProductDao productDaoMock;
	private ProductServiceImpl productService;
	
	@Before
	public void setUp() throws Exception {
		productDaoMock = Mockito.mock(ProductDao.class);
		productService = new ProductServiceImpl(productDaoMock);
	}
	
	
	// Hay problemas con el local date: hacer una clase mock aparte
	@Test
	public void createProductTest() {
//		Product product = dummyProduct(0);
//		Mockito.when(productDaoMock.createProduct(product.getName(), product.getDescription(), product.getShortDescription(), 
//				product.getUploadDate(), imageFromProduct(product), 0)).thenReturn(product);
//		
//		Product createdProduct = productService.createProduct(product.getName(), product.getDescription(), 
//				product.getShortDescription(), imageFromProduct(product), 0);
//		
//		assertIdenticalProducts(product, createdProduct);
//		
//		Mockito.verify(productDaoMock, Mockito.times(1)).createProduct(product.getName(), product.getDescription(), product.getShortDescription(), 
//				product.getUploadDate(), imageFromProduct(product), 0);
	}

	@Test
	public void testGetCreatorByProductId() {
		int productId = 1;
		User expectedUser = new User(1, "tomas", "cerda");
		Mockito.when(productDaoMock.getCreatorByProductId(productId)).thenReturn(expectedUser);
		
		User retrievedUser = productService.getCreatorByProductId(productId);
		assertEquals(expectedUser, retrievedUser);
		
		Mockito.verify(productDaoMock, Mockito.times(1)).getCreatorByProductId(productId);
	}
	
	@Test
	public void testGetProducts() {
		List<Product> expectedProducts = generateProducts();
		Mockito.when(productDaoMock.getProducts()).thenReturn(expectedProducts);
		
		List<Product> retrievedProducts = productService.getProducts();
		
		int i;
		for (i = 0; i < expectedProducts.size() && i < retrievedProducts.size(); i++)
			assertIdenticalProducts(expectedProducts.get(i), retrievedProducts.get(i));
		
		assertEquals(i, expectedProducts.size());
		assertEquals(i, retrievedProducts.size());
		
		Mockito.verify(productDaoMock, Mockito.times(1)).getProducts();
	}

	@Test
	public void getLogoByProductId() {
		Product dummyProduct = dummyProduct(0);
		byte[] expectedImage = imageFromProduct(dummyProduct);
		
		Mockito.when(productDaoMock.getLogoByProductId(0)).thenReturn(expectedImage);
		
		byte[] retrievedImage = productService.getLogoByProductId(0);
		
		assertEquals(expectedImage, retrievedImage);
		
		Mockito.verify(productDaoMock, Mockito.times(1)).getLogoByProductId(0);
	}


	private void assertIdenticalProducts(Product expectedProduct, Product retrievedProduct) {
		assertEquals(expectedProduct.getId(), retrievedProduct.getId());
		assertEquals(expectedProduct.getDescription(), retrievedProduct.getDescription());
		assertEquals(expectedProduct.getShortDescription(), retrievedProduct.getShortDescription());
		assertEquals(expectedProduct.getName(), retrievedProduct.getName());
	}
	
	private List<Product> generateProducts() {
		List<Product> productList = new ArrayList<Product>(NUMBER_PRODUCTS);
		for (int i = 1; i <= NUMBER_PRODUCTS; i++)
			productList.add(dummyProduct(i));
		return productList;
	}

	private Product dummyProduct(int id) {
		return new Product(id, "Product Tests " + id, "Test your product and be glorious " + id, "Come test! " + id, LocalDateTime.now());
	}
	
	private byte[] imageFromProduct(Product product) {
		return product.getName().getBytes();
	}
}
