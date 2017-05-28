package tp.paw.khet.model;

public enum Category {
	APP ("app"),
	ART ("art"),
	BOOK ("book"),
	FASHION ("fashion"),
	FILM ("film"),
	FOOD ("food"),
	GADGET ("gadget"),
	GAME ("game"),
	MUSIC ("music"),
	OTHER ("other");
	
	private final String name;
	
	private Category(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getLowerName() {
		return name;
	}
}
