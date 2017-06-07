package tp.paw.khet.model;

public enum ProductSortCriteria {
	ALPHABETICALLY ("alphabetically"),
	POPULARITY ("popularity"),
	MOST_RECENT ("recent");
	
	private final String name;
	
	private ProductSortCriteria(String name) {
		this.name = name;
	}
	
	public String getLowerName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
