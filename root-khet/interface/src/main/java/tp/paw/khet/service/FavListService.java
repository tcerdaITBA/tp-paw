package tp.paw.khet.service;

import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

public interface FavListService {

	/**
	 * Creates a {@link FavList} with no products.
	 * 
	 * @param name
	 *            - name of the new favList
	 * @param creatorId
	 *            - ID of the {@link User} who created the favList
	 * @return The created favList
	 * @throws DuplicateFavListException
	 *             - if the user already has a favList with the same name
	 */
	public FavList createFavList(String name, int creatorId) throws DuplicateFavListException;

	/**
	 * Deletes a {@link FavList}
	 * 
	 * @param favListId
	 *            - ID of the favList to delete
	 * @return true if the favList was deleted, false otherwise
	 */
	public boolean deleteFavList(int favListId);

	/**
	 * Retrieves a {@link FavList} given it's ID.
	 * 
	 * @param id
	 *            - ID of the favList to retrieve
	 * @return FavList with the associated ID or null if it doesn't exist
	 */
	public FavList getFavListById(int favListId);

	/**
	 * Adds a {@link Product} to a {@link FavList}
	 * 
	 * @param favListId
	 *            - ID of the favList
	 * @param productId
	 *            - ID of the product which should be added to favList
	 * @return true if product was succesfully added, false otherwise
	 */
	public boolean addProductToFavList(int favListId, int productId);

	/**
	 * Removes a {@link Product} from a {@link FavList}.
	 * 
	 * @param favListId
	 *            - ID of the favList
	 * @param productId
	 *            - ID of the product to remove from favList
	 * @return true if product was succesfully removed, false otherwise
	 */
	public boolean removeProductFromFavList(int favListId, int productId);
}
