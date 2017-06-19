package tp.paw.khet.exception;

@SuppressWarnings("serial")
public class DuplicateFavListException extends Exception {

	public DuplicateFavListException() {
		super();
	}
	
	public DuplicateFavListException(final String message) {
		super(message);
	}
}
