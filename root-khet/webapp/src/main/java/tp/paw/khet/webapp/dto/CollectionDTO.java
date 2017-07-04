package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;

@XmlRootElement
public class CollectionDTO {
	private int id;
	private String name;
	private List<PlainProductDTO> products;
	private int count;
	
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
		creatorURL = baseUri.resolve("users/" + id);

		for (Product p: favList.getProductList())
			products.add(new PlainProductDTO(p, baseUri));
		
		count = products.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
