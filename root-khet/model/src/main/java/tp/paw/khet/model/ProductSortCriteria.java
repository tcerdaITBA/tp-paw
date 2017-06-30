package tp.paw.khet.model;

import java.util.Comparator;

public enum ProductSortCriteria {
	ALPHABETICALLY((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())), 
	POPULARITY((p1, p2) -> Integer.compare(p2.getVotesCount(), p1.getVotesCount())), 
	RECENT((p1, p2) -> p2.getUploadDate().compareTo(p1.getUploadDate()));

	private final Comparator<Product> comparator;

	private ProductSortCriteria(final Comparator<Product> comparator) {
		this.comparator = comparator;
	}

	public Comparator<Product> getComparator() {
		return comparator;
	}

	public String getLowerName() {
		return this.name().toLowerCase();
	}
}
