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
	private FavListDao favListDao;

	@Override
	@Transactional
	public FavList createFavList(String name, int creatorId) {
		final User creator = userService.getUserById(creatorId);
		return creator.createFavList(name);		
	}

	@Override
	@Transactional
	public void deleteFavList(int favListId) {
		final FavList favList = favListDao.getFavListById(favListId); // TODO: cequear por null
		final User creator = favList.getCreator();
		
		creator.deleteFavList(favList);
	}

	@Override
	public FavList getFavListById(int favListId) {
		return favListDao.getFavListById(favListId);
	}

}