package tp.paw.khet.persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.Product;
import tp.paw.khet.Product.ProductBuilder;
import tp.paw.khet.persistence.rowmapper.PlainProductRowMapper;
import tp.paw.khet.persistence.rowmapper.ProductBuilderRowMapper;

@Repository
public class ProductJdbcDao implements ProductDao {
	
	@Autowired
	private ProductBuilderRowMapper productBuilderRowMapper;
	
	@Autowired
	private PlainProductRowMapper plainProductRowMapper;
	
	private JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public ProductJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("products")
					.usingGeneratedKeyColumns("productid");		
	}
	
	@Override
	public List<Product> getPlainProducts() {
		return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products ORDER BY uploadDate DESC", 
				plainProductRowMapper);
	}

	@Override
	public List<Product> getPlainProductsByCategory(String category) {
		return jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE category = ? ORDER BY uploadDate DESC", 
				plainProductRowMapper, category.toUpperCase(Locale.ENGLISH));
	}
	
	@Override
	public Product.ProductBuilder getFullProductById(int productId) {
		List<Product.ProductBuilder> productBuilder = jdbcTemplate.query("SELECT * FROM products NATURAL JOIN users WHERE productId = ?", productBuilderRowMapper, productId);
		
		if (productBuilder.isEmpty())
			return null;
		
		return productBuilder.get(0);
	}
	
	@Override
	public Product getPlainProductById(int productId) {
		List<Product> product = jdbcTemplate.query("SELECT productId, productName, shortDescription, category FROM products WHERE productId = ?", 
				plainProductRowMapper, productId);
		
		if (product.isEmpty())
			return null;
		
		return product.get(0);
	}
	
	@Override
	public ProductBuilder createProduct(String name, String description, String shortDescription, String website, String category,
			LocalDateTime uploadDate, byte[] logo, int creatorId) {

		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("productName", name);
		args.put("description", description);
		args.put("shortDescription", shortDescription);
		args.put("website", website);
		args.put("category", category.toUpperCase(Locale.ENGLISH));
		args.put("uploadDate", Timestamp.valueOf(uploadDate));
		args.put("logo", logo);
		args.put("userId", creatorId);
		
		final Number productId = jdbcInsert.executeAndReturnKey(args);
		
		return Product.getBuilder(productId.intValue(), name, shortDescription)
				.description(description)
				.website(website)
				.category(category)
				.uploadDate(uploadDate);		
	}

	@Override
	public byte[] getLogoByProductId(int productId) {
		byte[] logo = jdbcTemplate.queryForObject("SELECT logo FROM products WHERE productId = ?", byte[].class, productId);
		
		if (logo == null)
			return new byte[0];
		
		return logo;
	}
}
