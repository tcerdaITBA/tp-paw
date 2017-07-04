package tp.paw.khet.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
			em.flush();   // In order to be able to catch exception
			return user;
		} catch (PersistenceException e) {
			throw new DuplicateEmailException("There already exists an user with email: " + email);
		}
	}

	@Override
	public User getUserByEmail(final String email) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.email = :email", User.class);
		query.setParameter("email", email);

		final List<User> list = query.getResultList();
		
		if (list.isEmpty())
			return null;

		final User user = list.get(0);
		
		return user;
	}

	@Override
	public User getUserById(final int userId) {
		final User user = getPlainUserById(userId);

		// Hibernate LAZY fetch
		
		if (user != null) {
			user.getFavLists().size();
			user.getVotedProducts().size();
		}

		return user;
	}

	private User getPlainUserById(final int userId) {
		return em.find(User.class, userId);
	}

	@Override
	public byte[] getProfilePictureByUserId(final int userId) {
		final User user = getPlainUserById(userId);

		return user == null ? new byte[0] : user.getProfilePicture();
	}

	@Override
	public int getTotalUsersByKeyword(final Set<String> keywords) {
		final Query query = buildKeywordTypedQuery(keywords, true);
		return ((Long) query.getSingleResult()).intValue();
	}
	
	@Override
	public List<User> getUsersByKeyword(final Set<String> keywords, final int offset, final int length) {
		final Query query = buildKeywordTypedQuery(keywords, false);
		return pagedResult(query, offset, length);
	}
	
	private Query buildKeywordTypedQuery(final Set<String> keywords, final boolean isCountQuery) {
		final Map<String, String> keyWordsRegExp = new HashMap<>();
		final String keywordQuery;
		
		if (isCountQuery)
			keywordQuery = userKeywordQueryBuilder.buildCountQuery(keywords, keyWordsRegExp);
		else
			keywordQuery = userKeywordQueryBuilder.buildQuery(keywords, keyWordsRegExp);

		final Query query = em.createQuery(keywordQuery);

		for (final Entry<String, String> e : keyWordsRegExp.entrySet())
			query.setParameter(e.getKey(), e.getValue());	
		
		return query;
	}
	
	@Override
	public User changePassword(final int userId, final String password) {
		final User user = getPlainUserById(userId);

		if (user != null)
			user.setPassword(password);

		return user;
	}

	@Override
	public User changeProfilePicture(final int userId, final byte[] profilePicture) {
		final User user = getPlainUserById(userId);

		if (user != null)
			user.setProfilePicture(profilePicture);

		return user;
	}

	@SuppressWarnings("unchecked")
	private List<User> pagedResult(final Query query, final int offset, final int length) {
		query.setFirstResult(offset);
		query.setMaxResults(length);
		return (List<User>) query.getResultList();
	}
}
