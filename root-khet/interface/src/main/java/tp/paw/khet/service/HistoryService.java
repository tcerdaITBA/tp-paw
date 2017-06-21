package tp.paw.khet.service;

import java.util.Stack;

public interface HistoryService {

	// TODO: javadoc
	public Stack<String> saveQueryInHistory(Stack<String> history, String query);
}
