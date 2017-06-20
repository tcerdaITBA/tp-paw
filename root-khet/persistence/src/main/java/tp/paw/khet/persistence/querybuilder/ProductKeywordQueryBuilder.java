package tp.paw.khet.persistence.querybuilder;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class ProductKeywordQueryBuilder {

	private final static String FIRST_SEARCH_FIELD = "lower(p.name)";
	private final static String SECOND_SEARCH_FIELD = "lower(p.shortDescription)";

	private final String[] fields ={FIRST_SEARCH_FIELD, SECOND_SEARCH_FIELD};

	public String buildQuery(final Set<String> keywords, final Map<String, String> keyWordsRegExp) {
	    final StringBuilder whereQueryBuilder = new StringBuilder();
	    boolean queryPutAnd = false;
	 
	    for (final String keyword : keywords) {
    		if (queryPutAnd)
	    		whereQueryBuilder.append(" AND ");

    	    for (int i = 0; i < fields.length; i++) {
    	    	
    	    	final String candidateKeyWord = keyword.toLowerCase();
    	    	final String firstKeyWord = "first" + candidateKeyWord;
    	    	final String otherKeyWord = "other" + candidateKeyWord;
    	    	final String firstKeyWordRegExp = candidateKeyWord + "%";
    	    	final String otherKeyWordRegExp = "% " + candidateKeyWord + "%";

    	    	if (i == 0) {
	        		whereQueryBuilder.append("(");
		        	keyWordsRegExp.put(firstKeyWord, firstKeyWordRegExp);
		        	keyWordsRegExp.put(otherKeyWord, otherKeyWordRegExp);
    	    	}
	        	else	
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
	        	
	        	if (i == fields.length-1)
	        		whereQueryBuilder.append(")");		        	
	        	
	        	queryPutAnd = true;
	        }
	    }
	    
	    return whereQueryBuilder.toString();
	}
}
