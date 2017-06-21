package tp.paw.khet.service;

import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.model.comparator.UserAlphaComparator;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public boolean voteProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);

		if (product == null || user == null)
			return false;

		return user.voteProduct(product);
	}

	@Override
	@Transactional
	public boolean unvoteProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);

		if (product == null || user == null)
			return false;

		return user.unvoteProduct(product);
	}

	@Override
	@Transactional
	public boolean toggleVoteFromProduct(final int productId, final int userId) {
		final Product product = productService.getPlainProductById(productId);
		final User user = userService.getUserById(userId);

		if (product == null || user == null)
			return false;

		final SortedSet<Product> votedProducts = user.getVotedProducts();

		if (votedProducts.contains(product))
			return user.unvoteProduct(product);
		else
			return user.voteProduct(product);
	}

	@Override
	public SortedSet<User> getAlphabeticallySortedVotersFromProduct(final Product product, final int votersToShow) {
		final SortedSet<User> voters = new TreeSet<>(new UserAlphaComparator());

		int i = 0;
		for (User user : product.getVotingUsers()) {
			if (i++ == votersToShow)
				break;

			voters.add(user);
		}

		return voters;
	}
}
