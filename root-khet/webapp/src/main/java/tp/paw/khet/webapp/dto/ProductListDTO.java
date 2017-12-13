package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.User;

@XmlRootElement
public class ProductListDTO {
	private List<PlainProductDTO> products;
	private int totalCount;
	private int count;

	public ProductListDTO() {
	}

	public ProductListDTO(final List<Product> products, int totalCount, final URI baseUri, final Optional<User> loggedUser) {
		this.products = new LinkedList<>();

		for (final Product p : products)
			this.products.add(new PlainProductDTO(p, baseUri, loggedUser));

		this.totalCount = totalCount;
		this.count = products.size();
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
