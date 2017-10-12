package tp.paw.khet.webapp.dto.form;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import tp.paw.khet.model.Category;
import tp.paw.khet.webapp.form.constraints.NoDuplicates;

public class FormProduct {
	@NotBlank
	@Size(max = 64, min = 4)
	private String name;

	@NotBlank
	@Size(max = 8000)
	private String description;

	@NotBlank
	@Size(max = 140)
	private String tagline;

	@URL
	@Size(max = 512)
	private String website;

	@Valid
	@NoDuplicates
	@XmlElement(name = "video_ids")
	private String[] videoIds;

	@Pattern(regexp="app|art|book|fashion|film|food|gadget|game|music|other", flags = Pattern.Flag.CASE_INSENSITIVE)
	private String category;

	public FormProduct() {}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getTagline() {
		return tagline;
	}

	public String getWebsite() {
		return website;
	}

	public void setName(String name) {
		this.name = StringUtils.strip(name);
	}

	public void setDescription(String description) {
		this.description = StringUtils.strip(description);
	}

	public void setTagline(String tagline) {
		this.tagline = StringUtils.strip(tagline);
	}

	public void setWebsite(String url) {
		url = StringUtils.strip(url);

		if (url.length() > 0)
			url = StringUtils.prependIfMissingIgnoreCase(url, "http://", "https://");

		this.website = url;
	}

	public String[] getVideoIds() {
		int count = 0;
		
		for (int i = 0; i < videoIds.length; i++)
			if (videoIds[i] != null)
				count++;
		
		final String[] ans = new String[count];
		
		for (int i = 0; i < videoIds.length; i++)
			if (videoIds[i] != null)
				ans[i] = videoIds[i];
		
		return videoIds == null ? ArrayUtils.EMPTY_STRING_ARRAY : ans;
	}

	public void setVideoIds(String[] videos) {
		this.videoIds = videos;
	}

	public String getCategory() {
		return category;
	}
	
	public Category getAsCategory() {
		return Category.fromString(category);
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
