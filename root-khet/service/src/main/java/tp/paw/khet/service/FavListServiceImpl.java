package tp.paw.khet.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateFavListException;
import tp.paw.khet.model.FavList;
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
		final FavList favList = new FavList(name, creator);
		
		if (favLists.contains(favList))
			throw new DuplicateFavListException("There already exists a favList with the name " + name + " in user " + creator + " posession");
		
		return creator.addFavList(favList);		
	}

	@Override
	@Transactional
	public boolean deleteFavList(final int favListId) {
		final FavList favList = favListDao.getFavListById(favListId);
		final User creator = favList.getCreator();
		
		return creator.deleteFavList(favList);
	}

	@Override
	public FavList getFavListById(final int favListId) {
		return favListDao.getFavListById(favListId);
	}
	
	@Override
	public FavList getFavListByIdWithCreator(final int favListId) {
		return favListDao.getFavListByIdWithCreator(favListId);
	}
	
	@Override
	@Transactional
	public void addProductToFavList(final int favListId, final int productId) {
		getFavListById(favListId).addProduct(productService.getPlainProductById(productId));
	}

	@Override
	@Transactional
	public void removeProductFromFavList(final int favListId, final int productId) {
		getFavListById(favListId).removeProduct(productService.getPlainProductById(productId));
	}
}