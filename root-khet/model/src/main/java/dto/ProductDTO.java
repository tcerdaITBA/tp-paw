package dto;

import java.util.Date;
import java.util.List;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;
import tp.paw.khet.model.Video;

public class ProductDTO {

    private int id;
    private String name;
    private String description;
    private String shortDescription;
    private String website;
    private Category category;
    private Date uploadDate;
    private String logoURL;
    private UserDTO creator;
    
    //TODO: no
    private List<Video> videoURLs;
//    private List<String> imageURLs;

    public ProductDTO (Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        shortDescription = product.getShortDescription();
        website = product.getWebsite();
        category = product.getCategory();
        uploadDate = product.getUploadDate();
        
        StringBuilder logoSb = new StringBuilder();
        logoURL = logoSb.append("/product/").append(id).append("/logo").toString();
        
        videoURLs = product.getVideos();
    }
    
    public ProductDTO() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public UserDTO getCreator() {
        return creator;
    }

    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }

    public List<Video> getVideoURLs() {
        return videoURLs;
    }

    public void setVideoURLs(List<Video> videoURLs) {
        this.videoURLs = videoURLs;
    }
    
    
    
}
