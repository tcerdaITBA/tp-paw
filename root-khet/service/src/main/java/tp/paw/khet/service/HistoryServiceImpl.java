package tp.paw.khet.service;

import java.util.Stack;

import org.springframework.stereotype.Service;

import structures.FixedLengthStack;

@Service
public class HistoryServiceImpl implements HistoryService {

    private static final int HISTORY_MAX_SIZE = 3;
    
    @Override
    public Stack<String> saveQueryInHistory(Stack<String> history, String query) {
        if (history == null) {
            history = new FixedLengthStack<String>(HISTORY_MAX_SIZE);
        }
        
        if (!history.contains(query))
            history.push(query);
        
        return history;
    }

}
