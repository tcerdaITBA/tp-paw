package tp.paw.khet.webapp.form;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import tp.paw.khet.Category;
import tp.paw.khet.webapp.form.constraints.FileMediaType;
import tp.paw.khet.webapp.form.constraints.FileSize;
import tp.paw.khet.webapp.form.constraints.NoDuplicateVideos;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;

public class FormProduct {
	private static final int MAX_IMAGES = 4;
	private static final int MAX_VIDEOS = 2;
	
	@Size(max = 64, min=4)
	private String name;
	
	@NotBlank
	@Size(max = 8000)
	private String description;
	
	@NotBlank
	@Size(max = 140)
	private String shortDescription;
	
	@FileMediaType({MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})	
	@FileSize(min = 1)
	private MultipartFile logo;
	
	@URL
	@Size(max = 512)
	private String website;
	
	@Valid
	private MultipartFileImageWrapper[] images;
	
	@Valid
	@NoDuplicateVideos
	private VideoStringWrapper[] videos;
	
	private Category category = Category.OTHER;

	public FormProduct() {
		images = new MultipartFileImageWrapper[MAX_IMAGES];
		videos = new VideoStringWrapper[MAX_VIDEOS];
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getShortDescription() {
		return shortDescription;
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

	public void setShortDescription(String shortDescription) {
		this.shortDescription = StringUtils.strip(shortDescription);
	}
	
	public void setWebsite(String url) {
		url = StringUtils.strip(url);
		
		if (url.length() > 0)
			url = StringUtils.prependIfMissingIgnoreCase(url, "http://", "https://");
		
	    this.website = url;
	}

	public MultipartFile getLogo() {
		return logo;
	}
	
	public void setLogo(MultipartFile logo){
		this.logo = logo;
	}

	public MultipartFileImageWrapper[] getImages() {
		return images;
	}

	public void setImage(MultipartFileImageWrapper[] images) {
		this.images = images;
	}

	public VideoStringWrapper[] getVideos() {
		return videos;
	}

	public void setVideos(VideoStringWrapper[] videos) {
		this.videos = videos;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
}
