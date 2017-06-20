package tp.paw.khet.model.comparator;

import java.util.Comparator;

import tp.paw.khet.model.Product;

public class ProductAlphaComparator implements Comparator<Product> {

	@Override
	public int compare(final Product o1, final Product o2) {
		int cmp = o1.getName().compareToIgnoreCase(o2.getName());
		
		return cmp != 0 ? cmp : Integer.compare(o1.getId(), o2.getId());
	}

}
