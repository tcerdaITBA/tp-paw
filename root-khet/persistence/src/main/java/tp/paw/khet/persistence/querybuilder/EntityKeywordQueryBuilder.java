package tp.paw.khet.persistence.querybuilder;

import java.util.Map;
import java.util.Set;

import tp.paw.khet.persistence.querybuilder.KeywordQueryBuilder;

public abstract class EntityKeywordQueryBuilder implements KeywordQueryBuilder {

	public String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp, String[] fields) {
		final StringBuilder whereQueryBuilder = new StringBuilder();
		int keywordIdentifier = 0;
		boolean queryPutAnd = false;

		for (final String keyword : keywords) {
			if (queryPutAnd)
				whereQueryBuilder.append(" AND ");

			for (int i = 0; i < fields.length; i++) {

				final String candidateKeyWord = keyword.toLowerCase();
				final String firstKeyWord = "first" + keywordIdentifier;
				final String otherKeyWord = "other" + keywordIdentifier;
				final String firstKeyWordRegExp = candidateKeyWord + "%";
				final String otherKeyWordRegExp = "% " + candidateKeyWord + "%";

				if (i == 0) {
					whereQueryBuilder.append("(");
					keyWordsRegExp.put(firstKeyWord, firstKeyWordRegExp);
					keyWordsRegExp.put(otherKeyWord, otherKeyWordRegExp);
				} else
					whereQueryBuilder.append(" OR ");

				whereQueryBuilder.append("(");
				whereQueryBuilder.append(fields[i]);
				whereQueryBuilder.append(" LIKE ");
				whereQueryBuilder.append(":").append(firstKeyWord);

				whereQueryBuilder.append(" OR ");

				whereQueryBuilder.append(fields[i]);
				whereQueryBuilder.append(" LIKE ");
				whereQueryBuilder.append(":").append(otherKeyWord);
				whereQueryBuilder.append(")");

				if (i == fields.length - 1)
					whereQueryBuilder.append(")");

				queryPutAnd = true;
			}
		}
		
		keywordIdentifier++;

		return whereQueryBuilder.toString();
	}
}
