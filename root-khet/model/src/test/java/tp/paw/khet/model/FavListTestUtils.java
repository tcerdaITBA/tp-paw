package tp.paw.khet.model;

public final class FavListTestUtils {

	private FavListTestUtils() {
	}

	public static FavList dummyFavList(int favListId, User creator) {
		return new FavList(favListId, "Dummy FavList " + favListId, creator);
	}

}
