package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;

@XmlRootElement
public class CollectionDTO {
	private int id;
	
	@NotBlank
	@Size(min = 4, max = 64)
	private String name;
	private List<PlainProductDTO> products;
	private int count;
	
	private URI url;
	
	@XmlElement(name = "creator_url")
	private URI creatorURL;
	
	@XmlElement(name = "creation_date")
	private Date creationDate;
	
	public CollectionDTO() {};
	
	public CollectionDTO(final FavList favList, final URI baseUri) {
		id = favList.getId();
		creationDate = favList.getCreationDate();
		name = favList.getName();
		products = new ArrayList<>();
		setUrl(baseUri.resolve("collections/" + id));
		creatorURL = baseUri.resolve("users/" + id);

		for (Product p: favList.getProductList())
			products.add(new PlainProductDTO(p, baseUri));
		
		count = products.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.strip(name);
	}

	public List<PlainProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<PlainProductDTO> products) {
		this.products = products;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public URI getUrl() {
		return url;
	}

	public void setUrl(URI url) {
		this.url = url;
	}
}
