package tp.paw.khet.model;

public enum Category {
	APP, ART, BOOK, FASHION, FILM, FOOD, GADGET, GAME, MUSIC, OTHER;

	public String getLowerName() {
		return this.name().toLowerCase();
	}

	public static Category fromString(final String str) {
		return valueOf(str.toUpperCase());
	}
}
