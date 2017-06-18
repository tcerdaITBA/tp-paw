package tp.paw.khet.model.comparator;

import java.util.Comparator;

import tp.paw.khet.model.FavList;

public class FavListDateComparator implements Comparator<FavList> {

	@Override
	public int compare(final FavList o1, final FavList o2) {
		int cmp = o1.getCreationDate().compareTo(o2.getCreationDate());
		
		return cmp != 0 ? cmp : Integer.compare(o1.getId(), o2.getId());		
	}

}
