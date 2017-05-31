package tp.paw.khet.service;

import java.util.Collection;
import org.springframework.stereotype.Service;

import structures.ArrayQueue;

@Service
public class HistoryServiceImpl implements HistoryService {

    private static final int HISTORY_MAX_SIZE = 3;
    
    @Override
    public Collection<String> saveQueryInHistory(Collection<String> history, String query) {
        if (history == null) {
            history = new ArrayQueue<String>(String.class, HISTORY_MAX_SIZE);
        }
        
        history.add(query);
        return history;
    }

}
