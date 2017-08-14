package tp.paw.khet.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.exception.DuplicateEmailException;
import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;
import tp.paw.khet.persistence.UserDao;

@Service
public class UserServiceImpl implements UserService {

	private static final int MIN_WORD_SIZE = 3;

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(rollbackFor = DuplicateEmailException.class)
	public User createUser(final String userName, final String email, final String password, final byte[] profilePicture) throws DuplicateEmailException {
		return userDao.createUser(userName, email.toLowerCase(), password, profilePicture);
	}

	@Override
	public User getUserByEmail(final String email) {
		return userDao.getUserByEmail(email);
	}

	@Override
	public User getUserById(final int userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public int getMaxUserPageByKeyword(final String keyword, final int pageSize) {
		final Set<String> validKeywords = buildValidKeywords(keyword);

		if (validKeywords.isEmpty())
			return 0;
		
		final int total = userDao.getTotalUsersByKeyword(validKeywords);
		return maxPage(total, pageSize);
	}
	
	@Override
	public List<User> getUsersByKeyword(final String keyword, final int page, final int pageSize) {
		final Set<String> validKeywords = buildValidKeywords(keyword);
		
		if (validKeywords.isEmpty())
			return new ArrayList<User>();

		return userDao.getUsersByKeyword(validKeywords, (page - 1) * pageSize, pageSize);
	}
	
	private Set<String> buildValidKeywords(final String keyword) {
		final String[] keywords = keyword.trim().split(" ");
		final Set<String> validKeywords = new HashSet<>();

		for (final String word : keywords)
			if (word.length() >= MIN_WORD_SIZE)
				validKeywords.add(word);

		return validKeywords;
	}

	@Override
	public byte[] getProfilePictureByUserId(final int userId) {
		return userDao.getProfilePictureByUserId(userId);
	}

	@Override
	@Transactional
	public User changePassword(final int userId, final String password) {
		return userDao.changePassword(userId, password);
	}

	@Override
	@Transactional
	public User changeProfilePicture(final int userId, final byte[] profilePicture) {
		return userDao.changeProfilePicture(userId, profilePicture);
	}

	@Override
	@Transactional
	public int getMaxFavListsPageWithSize(int userId, int pageSize) {
		final User user = getUserById(userId);
		return maxPage(user.getFavLists().size(), pageSize);
	}

	@Override	
	public List<FavList> getFavListsByUserId(int userId, int page, int pageSize) {
		return userDao.getFavListsRange(userId, offset(page, pageSize), pageSize);
	}

	@Override
	@Transactional
	public int getMaxVotedProductsPageWithSize(int userId, int pageSize) {
		final User user = getUserById(userId);
		return maxPage(user.getVotedProducts().size(), pageSize);
	}

	@Override
	public List<Product> getVotedProductsByUserId(int userId, int page, int pageSize) {
		return userDao.getVotedProductsRange(userId, offset(page, pageSize), pageSize);
	}

	@Override
	public int getMaxCreatedProductsPageWithSize(int userId, int pageSize) {
		return maxPage(userDao.getTotalCreatedProductsByUserId(userId), pageSize);
	}

	@Override
	public List<Product> getCreatedProductsByUserId(int userId, int page, int pageSize) {
		return userDao.getCreatedProductsRange(userId, offset(page, pageSize), pageSize);
	}
	
	private int maxPage(final int total, final int pageSize) {
		return (int) Math.ceil((float) total / pageSize);
	}
	
	private int offset(final int page, final int pageSize) {
		return (page - 1) * pageSize;
	}
}