package tp.paw.khet.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static tp.paw.khet.model.ProductTestUtils.dummyProduct;
import static tp.paw.khet.model.UserTestUtils.dummyUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceImplTest {
	
	@Mock
	private ProductService productServiceMock;
	
	@Mock
	private UserService userServiceMock;
	
	@InjectMocks
	private VoteServiceImpl voteService;

	private Product product;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		product = dummyProduct(0);
		user = dummyUser(0);
		
		when(productServiceMock.getPlainProductById(0)).thenReturn(product);
		when(userServiceMock.getUserById(0)).thenReturn(user);
	}

	@Test
	public void voteProductTest() {
		assertEquals(0, product.getVotesCount());
		assertEquals(0, user.getVotedProducts().size());
		
		voteService.voteProduct(0, 0);
		
		assertEquals(1, product.getVotesCount());
		assertEquals(1, user.getVotedProducts().size());
		assertEquals(user, product.getVotingUsers().first());
		assertEquals(product, user.getVotedProducts().first());
		
		voteService.unvoteProduct(0, 0);
		
		assertEquals(0, product.getVotesCount());
		assertEquals(0, user.getVotedProducts().size());
	}

	@Test
	public void toggleVoteTest() {
		assertEquals(0, product.getVotesCount());
		assertEquals(0, user.getVotedProducts().size());
		
		voteService.toggleVoteFromProduct(0, 0);
		
		assertEquals(1, product.getVotesCount());
		assertEquals(1, user.getVotedProducts().size());
		assertEquals(user, product.getVotingUsers().first());
		assertEquals(product, user.getVotedProducts().first());
		
		voteService.toggleVoteFromProduct(0, 0);
		
		assertEquals(0, product.getVotesCount());
		assertEquals(0, user.getVotedProducts().size());
	}
}
