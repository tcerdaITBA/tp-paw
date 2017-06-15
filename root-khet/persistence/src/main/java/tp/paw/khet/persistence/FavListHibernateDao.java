package tp.paw.khet.persistence;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import tp.paw.khet.model.FavList;

@Repository
public class FavListHibernateDao implements FavListDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public FavList getFavListById(int favListId) {
		return em.find(FavList.class, favListId);
	}

	
}
