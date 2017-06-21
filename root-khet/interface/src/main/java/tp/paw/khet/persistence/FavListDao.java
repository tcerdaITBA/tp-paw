package tp.paw.khet.persistence;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.User;

public interface FavListDao {

	/**
	 * Retrieves a {@link FavList} given it's ID
	 * 
	 * @param favListId
	 *            - ID of the favList to be retrieved
	 * @return Product with the associated ID of null if it doesn't exist
	 */
	public FavList getFavListById(int favListId);

	/**
	 * Retrieves a {@link FavList} given it's ID populated with the
	 * corresponding {@link User} creator.
	 * 
	 * @param favListId
	 *            - ID of the favList
	 * @return FavList with the associated ID or null if it doesn't exist
	 */
	public FavList getFavListByIdWithCreator(int favListId);

}