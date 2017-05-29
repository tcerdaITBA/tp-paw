package tp.paw.khet.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import tp.paw.khet.model.Video;

@Repository
public class VideoHibernateDao implements VideoDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Video> getVideosByProductId(final int id) {
		final TypedQuery<Video> query = em.createQuery("from Video as v where v.productId = :productId", Video.class);
		query.setParameter("productId", id);
		
		return query.getResultList();
	}

	@Override
	public Video createVideo(final String videoId, final int productId) {
		final Video video = new Video(videoId, productId);
		
		em.persist(video);
		
		return video;
	}

}
