package tp.paw.khet.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

	@Override
	public FavList getFavListByIdWithCreator(int favListId) {
		final TypedQuery<FavList> query = em.createQuery("from FavList as fv join fetch fv.creator as c where fv.id = :id", FavList.class);

		query.setParameter("id", favListId);

		final List<FavList> list = query.getResultList();

		return list.isEmpty() ? null : list.get(0);
	}

}
