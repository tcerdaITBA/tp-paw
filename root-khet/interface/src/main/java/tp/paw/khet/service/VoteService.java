package tp.paw.khet.service;

import java.util.SortedSet;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface VoteService {

	/**
	 * Votes a {@link Product} given it's ID and the voter {@link User} ID. The
	 * product cannot be already voted by the user.
	 * 
	 * @param productId  - ID of the product to vote
	 * @param userId - ID of the voter
	 * @return true if the product was successfully voted and was not previously
	 *         voted, false otherwise
	 */
	public boolean voteProduct(int productId, int userId);

	/**
	 * Removes a vote from a {@link Product} given it's ID and the {@link User}
	 * ID who had voted the product.
	 * 
	 * @param productId - ID of the product to unvote
	 * @param userId - ID of the user who wants to remove his/her vote
	 * @return true if the product was successfully unvoted and was previously
	 *         voted, false otherwise
	 */
	public boolean unvoteProduct(int productId, int userId);

	/**
	 * Removes a vote from a {@link Product} if it was already voted or votes it
	 * otherwise
	 * 
	 * @param productId - ID of the product to unvote
	 * @param userId - ID of the user who wants to toggle it's vote
	 * @return true if the product vote was modified, false otherwise
	 */
	public boolean toggleVoteFromProduct(int productId, int userId);

	/**
	 * Retrieves an alphabetically sorted {@link SortedSet} of voters given a
	 * product
	 * 
	 * @param product - The {@link Product} which votes to retrieve
	 * @param page - index of the page to be retrieved
	 * @param pageSize - amount of voters per page
	 * @return a sorted set with the corresponding voters
	 */
	public SortedSet<User> getAlphabeticallySortedVotersFromProduct(Product product, int page, int pageSize);

	/**
	 * Retrieves the amount of voters pages available for a given page size.
	 * @param product - The {@link Product} whose amount of voters pages to retrieve
	 * @param pageSize - Amount of voters per page
	 * @return the maximum page number, which is the total number of pages for the given size.
	 */
	public int getMaxVotersPageWithSize(Product product, int pageSize);
}
