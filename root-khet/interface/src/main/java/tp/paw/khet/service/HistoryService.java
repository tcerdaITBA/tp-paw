package tp.paw.khet.service;

import java.util.Collection;

public interface HistoryService {
    
    //TODO: javadoc
    public Collection<String> saveQueryInHistory(Collection<String> history, String query);
}
