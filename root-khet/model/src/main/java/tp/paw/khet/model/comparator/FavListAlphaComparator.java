package tp.paw.khet.model.comparator;

import java.util.Comparator;

import tp.paw.khet.model.FavList;

public class FavListAlphaComparator implements Comparator<FavList> {

	@Override
	public int compare(final FavList o1, final FavList o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
