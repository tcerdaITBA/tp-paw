package tp.paw.khet.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.User;
import tp.paw.khet.persistence.querybuilder.UserKeywordQueryBuilder;

@Repository
public class UserHibernateDao implements UserDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserKeywordQueryBuilder userKeywordQueryBuilder;
	
	@Override
	public User createUser(final String userName, final String email, final String password, final byte[] profilePicture) throws DuplicateEmailException {
		final User user = new User(userName, email, password, profilePicture);
		
		try {
			em.persist(user);
			return user;
		} 
		catch (ConstraintViolationException e) {
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
		final User user = getUserById(userId);
		
		return user == null ? new byte[0] : user.getProfilePicture();
	}

	@Override
	public List<User> getUsersByKeyword(final Set<String> keywords, final int offset, final int length) {
		
		final Map<String, String> keyWordsRegExp = new HashMap<>();
		final String whereQuery = userKeywordQueryBuilder.buildQuery(keywords, keyWordsRegExp);
		
		final TypedQuery<User> query = em.createQuery("from User as u where " + whereQuery + " ORDER BY lower(u.name)", User.class);

		for (final Entry<String,String> e : keyWordsRegExp.entrySet())
			query.setParameter(e.getKey(), e.getValue());	
		
		return pagedResult(query, offset, length);
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

	@Override
	public User getUserWithVotedProductsById(final int userId) {
		final TypedQuery<User> query = em.createQuery("from User as u left join fetch u.votedProducts as uvp where u.userId = :userId", User.class);
		query.setParameter("userId", userId);
		
		final List<User> list = query.getResultList();
		
		if (list.isEmpty())
			return null;
		
		return list.get(0);
	}
	
    private List<User> pagedResult(final TypedQuery<User> query, final int offset, final int length) {
    	query.setFirstResult(offset);
    	query.setMaxResults(length);
    	return query.getResultList();
    }
}
