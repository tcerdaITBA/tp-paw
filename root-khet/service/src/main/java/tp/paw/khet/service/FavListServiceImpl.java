package tp.paw.khet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public FavList createFavList(final String name, final int creatorId) {
		final User creator = userService.getUserById(creatorId);
		return creator.createFavList(name);		
	}

	@Override
	@Transactional
	public void deleteFavList(final int favListId) {
		final FavList favList = favListDao.getFavListById(favListId); // TODO: cequear por null
		final User creator = favList.getCreator();
		
		creator.deleteFavList(favList);
	}

	@Override
	public FavList getFavListById(final int favListId) {
		return favListDao.getFavListById(favListId);
	}
	
	@Override
	@Transactional
	public void addProductToFavList(final int favListId, final int productId) {
		getFavListById(favListId).addProduct(productService.getPlainProductById(productId));
	}

	@Override
	@Transactional
	public void removeProductFromFavList(int favListId, int productId) {
		getFavListById(favListId).removeProduct(productService.getPlainProductById(productId));
	}

}