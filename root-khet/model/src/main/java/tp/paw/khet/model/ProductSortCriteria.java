package tp.paw.khet.model;

import java.util.Comparator;

public enum ProductSortCriteria {
	ALPHA((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName())), 
	VOTES((p1, p2) -> Integer.compare(p1.getVotesCount(), p2.getVotesCount())), 
	DATE((p1, p2) -> p1.getUploadDate().compareTo(p2.getUploadDate()));

	private final Comparator<Product> comparator;
	
	private ProductSortCriteria(final Comparator<Product> comparator) {
		this.comparator = comparator;
	}

	public static ProductSortCriteria fromString(final String str) {
		return valueOf(str.toUpperCase());
	}
	
	public Comparator<Product> getComparator(final OrderCriteria order) {
		return order == OrderCriteria.ASC ? comparator : comparator.reversed();
	}

	public String getLowerName() {
		return this.name().toLowerCase();
	}
}
