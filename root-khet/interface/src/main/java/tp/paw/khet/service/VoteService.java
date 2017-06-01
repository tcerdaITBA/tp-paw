package tp.paw.khet.service;

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
}
