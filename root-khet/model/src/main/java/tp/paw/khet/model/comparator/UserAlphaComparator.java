package tp.paw.khet.model.comparator;

import java.util.Comparator;

import tp.paw.khet.model.User;

public class UserAlphaComparator implements Comparator<User> {

	@Override
	public int compare(final User o1, final User o2) {
		int cmp = o1.getName().compareToIgnoreCase(o2.getName());

		return cmp != 0 ? cmp : Integer.compare(o1.getUserId(), o2.getUserId());
	}

}
