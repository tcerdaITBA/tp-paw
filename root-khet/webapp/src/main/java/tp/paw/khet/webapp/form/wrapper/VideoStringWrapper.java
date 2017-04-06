package tp.paw.khet.webapp.form.wrapper;

import java.util.regex.Matcher;

import javax.validation.constraints.Pattern;

public class VideoStringWrapper {

	private static final String YOUTUBE_REGEX = "(?:https:\\/\\/(?:www\\.)?)?(?:youtube\\.com\\/\\S*(?:(?:\\/e(?:mbed))?"
			+ "\\/|watch\\?(?:\\S*?&?v\\=))|youtu\\.be\\/)([a-zA-Z0-9_-]{11})|^$";
	
	private static final java.util.regex.Pattern COMPILED_PATTERN = java.util.regex.Pattern.compile(YOUTUBE_REGEX);

	
	@Pattern(regexp = YOUTUBE_REGEX)
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean hasUrl() {
		return url != null && url.length() > 0;
	}
	
	/**
	 * Extracts Youtube video's ID.
	 * @return id - Youtube video ID
	 */
	public String getVideoId() {
        Matcher matcher = COMPILED_PATTERN.matcher(url);
        if (matcher.find())
             return matcher.group(1);
        
        throw new IllegalStateException("URL should already be validated and regex should therefore match");
	}
}
