package tp.paw.khet.webapp.form;

import javax.validation.constraints.Size;

import java.util.regex.Matcher;

import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.MediaType;


import tp.paw.khet.webapp.form.constraints.FileMediaType;
import tp.paw.khet.webapp.form.constraints.FileSize;


public class FormProduct {
	private static final String YOUTUBE_REGEX = "(?:https:\\/\\/(?:www\\.)?)?(?:youtube\\.com\\/\\S*(?:(?:\\/e(?:mbed))?"
			+ "\\/|watch\\?(?:\\S*?&?v\\=))|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
	private static final int MAX_IMAGES = 4;
	private static final int MAX_VIDEOS = 2;
	
	private int id;
	
	@NotEmpty
	@Size(max = 64)
	private String name;
	
	@NotEmpty
	private String description;
	
	@NotEmpty
	@Size(max = 140)
	private String shortDescription;
	
	@FileMediaType({MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})	
	@FileSize(min = 1)
	private MultipartFile logo;
	
	//Debe ser un usuario
	@NotEmpty
	private String creatorName;
	
	@NotEmpty
	@Email
	private String creatorMail;
	
	private MultipartFile[] images;
	
	private String[] videos;
	
	@URL
	@Pattern(regexp = YOUTUBE_REGEX)
	private String video;

	public FormProduct() {
		images = new MultipartFile[MAX_IMAGES];
		videos = new String[MAX_VIDEOS];
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
	
	public String getCreatorMail() {
		return creatorMail;
	}
	
	public void setCreatorMail(String creatorMail) {
		this.creatorMail = creatorMail;
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

	public MultipartFile getLogo() {
		return logo;
	}
	
	public void setLogo(MultipartFile logo){
		this.logo = logo;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public MultipartFile[] getImages() {
		return images;
	}

	public void setImage(MultipartFile[] images) {
		this.images = images;
	}

	public String[] getVideos() {
		return videos;
	}

	public void setVideos(String[] videos) {
		this.videos = videos;
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

	public String getVideo() {
		return this.video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	/**
	 * Extrae el ID de un video de Youtube.
	 * @return id - ID del video de youtube
	 */
	public String getVideoId(){
		String pattern = YOUTUBE_REGEX;
        java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(video);
        if (matcher.find()) {
             return matcher.group(1);
        }
        //No deberia llegar a esta instancia, pues el formato de url fue validado por Spring
        
        throw new IllegalStateException("regex should match");
	}
}
