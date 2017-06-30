package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlRootElement;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;

@XmlRootElement
public class ProductListDTO {
	private List<PlainProductDTO> products;
	private int page;
	private int pageSize;
	private String category;

	public ProductListDTO() {
	};

	public ProductListDTO(final List<Product> products, final int page, final int pageSize, final Optional<Category> category, final URI baseUri) {
		this.products = new LinkedList<>();

		for (final Product p : products)
			this.products.add(new PlainProductDTO(p, baseUri));

		this.page = page;
		this.pageSize = pageSize;

		if (category.isPresent())
			this.category = category.get().getLowerName();
	}

	public List<PlainProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<PlainProductDTO> products) {
		this.products = products;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
