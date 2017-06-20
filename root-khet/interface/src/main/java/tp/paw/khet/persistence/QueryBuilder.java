package tp.paw.khet.persistence;

import java.util.Map;
import java.util.Set;

public interface QueryBuilder {
	String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp);
}
