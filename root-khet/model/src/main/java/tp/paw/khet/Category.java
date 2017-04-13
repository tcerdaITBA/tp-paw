package tp.paw.khet;

public enum Category {
	APP,
	ART,
	BOOK,
	FASHION,
	FILM,
	FOOD,
	GADGET,
	GAME,
	MUSIC,
	OTHER;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}
