package tp.paw.khet.service;

import java.util.SortedSet;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface VoteService {

	/**
	 * Votes a {@link Product} given it's ID and the voter {@link User} ID.
	 * The product cannot be already voted by the user.
	 * @param productId - ID of the product to vote
	 * @param userId - ID of the voter
	 */
	public void voteProduct(int productId, int userId);
	
	/**
	 * Removes a vote from a {@link Product} given it's ID and 
	 * the {@link User} ID who had voted the product.
	 * @param productId - ID of the product to unvote
	 * @param userId - ID of the user who wants to remove his/her vote
	 */
	public void unvoteProduct(int productId, int userId);

	/**
	 * Removes a vote from a {@link Product} if 
	 * it was already voted or votes it otherwise
	 * @param productId - ID of the product to unvote
	 * @param userId - ID of the user who wants to toggle it's vote
	 */
	public void toggleVoteFromProduct(int productId, int userId);

	/**
	 * Retrieves an alphabetically sorted {@link SortedSet} of voters given a product
	 * @param product - The {@Product} which votes to retrieve
	 * @param votersToShow - Maximum number of voters to retrieve
	 * @return a sorted set with the corresponding voters
	 */
	public SortedSet<User> getAlphabeticallySortedVotersFromProduct(Product product, int votersToShow);
}
