package tp.paw.khet.model;

public enum ProductSortCriteria {
	ALPHABETICALLY,
	POPULARITY,
	RECENT;
		
	public String getLowerName() {
		return this.name().toLowerCase();
	}
}
