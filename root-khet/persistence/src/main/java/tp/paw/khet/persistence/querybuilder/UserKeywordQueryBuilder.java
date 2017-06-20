package tp.paw.khet.persistence.querybuilder;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class UserKeywordQueryBuilder extends EntityKeywordQueryBuilder {

	private final static String FIRST_SEARCH_FIELD = "lower(u.name)";

	private final String[] fields ={FIRST_SEARCH_FIELD,};
	
	public String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp) {
		return buildQuery(keywords, keyWordsRegExp, fields);
	}
}
