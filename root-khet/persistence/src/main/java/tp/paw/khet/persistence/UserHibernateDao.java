package tp.paw.khet.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.User;

@Repository
public class UserHibernateDao implements UserDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public User createUser(final String userName, final String email, final String password, final byte[] profilePicture) throws DuplicateEmailException {
		final User user = new User(userName, email, password, profilePicture);
		
		try {
			em.persist(user);
			return user;
		} 
		catch (DuplicateKeyException e) {
			throw new DuplicateEmailException("There already exists an user with email: " + email);
		}
	}
	
	@Override
	public User getUserByEmail(final String email) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.email = :email", User.class);
		query.setParameter("email", email);
		
		final List<User> list = query.getResultList();
		
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public User getUserById(final int userId) {
		return em.find(User.class, userId);
	}

	@Override
	public byte[] getProfilePictureByUserId(final int userId) {
		final User user = em.find(User.class, userId);
		
		return user == null ? new byte[0] : user.getProfilePicture();
	}

	@Override
	public List<User> getUsersByKeyword(final String keyword, final int maxLength) {
		final TypedQuery<User> query = em.createQuery("from User as u where lower(u.name) LIKE lower(:firstWordKeyword) "
													  + "OR lower(u.name) LIKE lower(:otherWordsKeyword) "
													  + "order by lower(u.name)", User.class);
		
		final String firstWordKeyword = keyword+"%";
		final String otherWordsKeyword = "% "+keyword+"%";
		query.setParameter("firstWordKeyword", firstWordKeyword);
		query.setParameter("otherWordsKeyword", otherWordsKeyword);
		query.setMaxResults(maxLength);
		
		return query.getResultList();
	}
	
	@Override
	public User changePassword(final int userId, final String password) {
		final User user = getUserById(userId);
		
		if (user != null)
			user.setPassword(password);
		
		return user;
	}

	@Override
	public User changeProfilePicture(final int userId, final byte[] profilePicture) {
		final User user = getUserById(userId);
		
		if (user != null)
			user.setProfilePicture(profilePicture);
		
		return user;
	}
}
