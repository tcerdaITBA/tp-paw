package tp.paw.khet.model;

public enum OrderCriteria {
	ASC,
	DESC;
	
	public static OrderCriteria fromString(final String str) {
		return valueOf(str.toUpperCase());
	}
}
