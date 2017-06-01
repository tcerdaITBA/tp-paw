package tp.paw.khet.exception;

@SuppressWarnings("serial")
public class MissingVoteException extends RuntimeException {

	public MissingVoteException() {
		super();
	}
	
	public MissingVoteException(String message) {
		super(message);
	}
}
