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
		
		voteProduct(product, user);
	}

	private void voteProduct(final Product product, final User user) {
		final SortedSet<Product> votedProducts = user.getVotedProducts();
		
		votedProducts.add(product);
		product.getVotingUsers().add(user);		
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
		
		unvoteProduct(product, user);
	}
	
	private void unvoteProduct(final Product product, final User user) {
		final SortedSet<Product> votedProducts = user.getVotedProducts();
		
		votedProducts.remove(product);
		product.getVotingUsers().remove(user);		
	}
	
	@Transactional
	@Override
	public void toggleVoteFromProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);
		final SortedSet<Product> votedProducts = user.getVotedProducts();

		if (votedProducts.contains(product))
			unvoteProduct(product, user);
		else
			voteProduct(product, user);
	}

	@Override
	public SortedSet<User> getAlphabeticallySortedVotersFromProduct(final Product product, final int votersToShow) {
		final SortedSet<User> voters = new TreeSet<>(); // TODO: mover comparator a un lugar en comúm
		
		int i = 0;
		for (User user : product.getVotingUsers()) {
			if (i++ == votersToShow)
				break;
			
			voters.add(user);
		}
		
		return voters;
	}	
}
