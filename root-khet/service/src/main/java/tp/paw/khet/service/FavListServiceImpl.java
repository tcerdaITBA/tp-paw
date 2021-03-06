package tp.paw.khet.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.persistence.FavListDao;

@Service
public class FavListServiceImpl implements FavListService {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private FavListDao favListDao;

	@Override
	@Transactional
	public FavList createFavList(final String name, final int creatorId) throws DuplicateFavListException {
		final User creator = userService.getUserById(creatorId);
		final Set<FavList> favLists = creator.getFavLists();
		
		favLists.iterator(); // lazy fetch
		
		final FavList favList = new FavList(name, creator);

		if (favLists.contains(favList))
			throw new DuplicateFavListException("There already exists a favList with the name " + name + " in user " + creator + " posession");

		creator.addFavList(favList);

		return favList;
	}

	@Override
	@Transactional
	public boolean deleteFavList(final int favListId) {
		final FavList favList = getFavListById(favListId);

		if (favList == null)
			return false;

		final User creator = favList.getCreator();

		return creator.deleteFavList(favList);
	}

	@Override
	public FavList getFavListById(final int favListId) {
		return favListDao.getFavListById(favListId);
	}

	@Override
	@Transactional
	public boolean addProductToFavList(final int favListId, final int productId) {
		final FavList favList = getFavListById(favListId);
		final Product product = productService.getPlainProductById(productId);

		if (favList == null || product == null)
			return false;

		return favList.addProduct(product);
	}

	@Override
	@Transactional
	public boolean removeProductFromFavList(final int favListId, final int productId) {
		final FavList favList = getFavListById(favListId);
		final Product product = productService.getPlainProductById(productId);

		if (favList == null || product == null)
			return false;

		return favList.removeProduct(product);
	}
}