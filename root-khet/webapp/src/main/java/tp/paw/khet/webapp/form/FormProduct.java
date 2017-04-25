package tp.paw.khet.webapp.form;

import javax.validation.constraints.Size;

import javax.validation.Valid;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;

import tp.paw.khet.Category;
import tp.paw.khet.webapp.form.constraints.FileMediaType;
import tp.paw.khet.webapp.form.constraints.FileSize;
import tp.paw.khet.webapp.form.constraints.NoDuplicateVideos;
import tp.paw.khet.webapp.form.wrapper.MultipartFileImageWrapper;
import tp.paw.khet.webapp.form.wrapper.VideoStringWrapper;


public class FormProduct {
	private static final int MAX_IMAGES = 4;
	private static final int MAX_VIDEOS = 2;
	
	private int id;
	
	@Size(max = 64, min=4)
	private String name;
	
	@NotEmpty
	@Size(max = 8000)
	private String description;
	
	@NotEmpty
	@Size(max = 140)
	private String shortDescription;
	
	@FileMediaType({MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})	
	@FileSize(min = 1)
	private MultipartFile logo;
	
	@URL //TODO: no valida sin http://
	@Size(max = 1000)
	private String website;
	
	@Valid
	private FormUser formUser = new FormUser();
		
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
	
	public FormUser getFormUser() {
		return formUser;
	}
	
	public void setFormUser(FormUser formUser) {
		this.formUser = formUser;
	}
	
	public int getId() {
		return id;
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
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public void setWebsite(String url) {
	    this.website = url;
	}

	public MultipartFile getLogo() {
		return logo;
	}
	
	public void setLogo(MultipartFile logo){
		this.logo = logo;
	}

	public String getUserName() {
		return formUser.getUserName();
	}

	public String getUserEmail() {
		return formUser.getUserEmail();
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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;
		
		FormProduct other = (FormProduct) obj;
		
		return id == other.id;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
