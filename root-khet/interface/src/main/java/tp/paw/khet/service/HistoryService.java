package tp.paw.khet.service;

import java.util.Stack;

public interface HistoryService {

	Stack<String> saveQueryInHistory(Stack<String> history, String query);
}
