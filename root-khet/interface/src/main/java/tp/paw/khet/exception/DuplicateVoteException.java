package tp.paw.khet.exception;

@SuppressWarnings("serial")
public class DuplicateVoteException extends RuntimeException {

	public DuplicateVoteException() {
		super();
	}
	
	public DuplicateVoteException(String message) {
		super(message);
	}
}
