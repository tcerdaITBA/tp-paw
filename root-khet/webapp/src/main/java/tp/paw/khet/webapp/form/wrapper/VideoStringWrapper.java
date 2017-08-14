package tp.paw.khet.webapp.form.wrapper;

import java.util.regex.Matcher;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;

public class VideoStringWrapper {

	private static final String YOUTUBE_REGEX = "(?:https:\\/\\/(?:www\\.)?)?(?:youtube\\.com\\/\\S*(?:(?:\\/e(?:mbed))?"
			+ "\\/|watch\\?(?:\\S*?&?v\\=))|youtu\\.be\\/)([a-zA-Z0-9_-]{11})(?:\\?t=(?:[0-9]+m)?[0-9]+s)?|^$";

	private static final java.util.regex.Pattern COMPILED_PATTERN = java.util.regex.Pattern.compile(YOUTUBE_REGEX);

	@Pattern(regexp = YOUTUBE_REGEX)
	private String url;

	public VideoStringWrapper(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = StringUtils.strip(url);
	}

	public boolean hasUrl() {
		return url != null && url.length() > 0;
	}

	public boolean hasValidUrl() {
		return hasUrl() && COMPILED_PATTERN.matcher(url).matches();
	}

	/**
	 * Extracts Youtube video's ID.
	 * 
	 * @return id - Youtube video ID
	 */
	public String getVideoId() {
		Matcher matcher = COMPILED_PATTERN.matcher(url);
		if (matcher.find())
			return matcher.group(1);

		throw new IllegalStateException("URL should already be validated and regex should therefore match");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof VideoStringWrapper))
			return false;

		VideoStringWrapper other = (VideoStringWrapper) obj;
		return url.equals(other.url);
	}

}
