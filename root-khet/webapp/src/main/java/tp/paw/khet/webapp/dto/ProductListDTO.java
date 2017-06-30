package tp.paw.khet.webapp.dto;

import java.util.List;
import java.util.LinkedList;
import java.util.Optional;

import tp.paw.khet.model.Category;
import tp.paw.khet.model.Product;

public class ProductListDTO {
    private List<ProductDTO> products;
    private int page;
    private int pageSize;
    private String category;
    
    public ProductListDTO() {};
    
    public ProductListDTO(List<Product> products, int page, int pageSize, Optional<Category> category) {
        this.products = new LinkedList<>();
        for (Product p: products) {
            this.products.add(new ProductDTO(p));
        }
        this.page = page;
        this.pageSize = pageSize;
        if (category.isPresent())
            this.category = category.get().getLowerName();
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
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
