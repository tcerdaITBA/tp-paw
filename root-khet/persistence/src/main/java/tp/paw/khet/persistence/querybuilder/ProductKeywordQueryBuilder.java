package tp.paw.khet.persistence.querybuilder;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ProductKeywordQueryBuilder extends EntityKeywordQueryBuilder {

	private final static String FIRST_SEARCH_FIELD = "lower(p.name)";
	private final static String SECOND_SEARCH_FIELD = "lower(p.shortDescription)";

	private final String[] fields = { FIRST_SEARCH_FIELD, SECOND_SEARCH_FIELD };

	public String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp) {
		return buildQuery(keywords, keyWordsRegExp, fields);
	}
}
