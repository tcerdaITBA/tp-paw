package tp.paw.khet.persistence;

import tp.paw.khet.model.FavList;

public interface FavListDao {

	/**
	 * Retrieves a {@link FavList} given it's ID
	 * @param favListId - ID of the favList to be retrieved
	 * @return Product with the associated ID of null if it doesn't exist
	 */
	public FavList getFavListById(int favListId);
	
}