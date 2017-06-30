package tp.paw.khet.webapp.dto;

import java.util.List;
import java.util.LinkedList;
import java.net.URI;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.Product;

public class CollectionDTO {
	private String name;
	private List<PlainProductDTO> products;
	
	public CollectionDTO() {};
	
	public CollectionDTO(FavList favlist, final URI baseUri) {
		name = favlist.getName();
		products = new LinkedList<>();
		for (Product p: favlist.getProductList()) {
			products.add(new PlainProductDTO(p, baseUri));
		}
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
}
