package tp.paw.khet.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.web.multipart.MultipartFile;

import tp.paw.khet.webapp.form.constraints.FileSize;

public class FormProduct {
	
	private int id;
	
	@NotNull
	@Size(max = 64)
	private String name;
	
	@NotNull
	private String description;
	
	@NotNull
	@Size(max = 140)
	private String shortDescription;
	
	@FileSize(min = 1)
	private MultipartFile logo;
	
	//Debe ser un usuario
	@NotNull
	private String creatorName;
	
	@NotNull
	@Email
	private String creatorMail;
	
	private MultipartFile image;
	private String video;

	
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

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
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
