package tp.paw.khet.persistence.querybuilder;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class UserKeywordQueryBuilder extends EntityKeywordQueryBuilder {

	private final static String FIRST_SEARCH_FIELD = "lower(u.name)";

	private final String[] fields = { FIRST_SEARCH_FIELD, };

	public String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp) {
		return buildPlainQuery(keywords, keyWordsRegExp) + " ORDER BY lower(u.name)";
	}
	
	public String buildCountQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp) {
		return "select count(*) " + buildPlainQuery(keywords, keyWordsRegExp);
	}
	
	private String buildPlainQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp) {
		return "from User as u where " + super.buildQuery(keywords, keyWordsRegExp, fields);
	}
}
