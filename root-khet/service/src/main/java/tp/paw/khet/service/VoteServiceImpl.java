package tp.paw.khet.service;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateVoteException;
import tp.paw.khet.exception.MissingVoteException;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	@Override
	public void voteProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);
		final SortedSet<Product> votedProducts = user.getVotedProducts();
		
		// TODO: probablemente quitar y hacer que salte excepción de SQL
		if (votedProducts.contains(product))
			throw new DuplicateVoteException("User " + user + " already voted product " + product);
		
		user.voteProduct(product);
	}
	
	@Transactional
	@Override
	public void unvoteProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);
		final SortedSet<Product> votedProducts = user.getVotedProducts();
		
		// TODO: probablemente quitar y hacer que salte excepción de SQL
		if (!votedProducts.contains(product))
			throw new MissingVoteException("User " + user + " has not voted product " + product);
		
		user.unvoteProduct(product);
	}
	
	@Transactional
	@Override
	public void toggleVoteFromProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);
		final SortedSet<Product> votedProducts = user.getVotedProducts();

		if (votedProducts.contains(product))
			user.unvoteProduct(product);
		else
			user.voteProduct(product);
	}

	@Override
	public SortedSet<User> getAlphabeticallySortedVotersFromProduct(final Product product, final int votersToShow) {
		final SortedSet<User> voters = new TreeSet<>();
		
		int i = 0;
		for (User user : product.getVotingUsers()) {
			if (i++ == votersToShow)
				break;
			
			voters.add(user);
		}
		
		return voters;
	}	
}
