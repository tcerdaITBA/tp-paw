package tp.paw.khet.persistence;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.Product;
import tp.paw.khet.User;
import tp.paw.khet.persistence.rowmapper.ProductRowMapper;
import tp.paw.khet.persistence.rowmapper.UserRowMapper;

@Repository
public class ProductJdbcDao implements ProductDao {
	
	private final static ProductRowMapper PRODUCT_ROW_MAPPER = ProductRowMapper.getInstance();
	private final static UserRowMapper USER_ROW_MAPPER = UserRowMapper.getInstance();
	
	private JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public ProductJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("products")
					.usingGeneratedKeyColumns("productid");		
	}
	
	public List<Product> getProducts() {
		return jdbcTemplate.query("SELECT * FROM products ORDER BY uploadDate DESC", PRODUCT_ROW_MAPPER);
	}

	public Product getProductByProductId(int id) {
		List<Product> product = jdbcTemplate.query("SELECT * FROM products WHERE productId = ?", PRODUCT_ROW_MAPPER, id);
		
		if (product.isEmpty())
			return null;
		
		return product.get(0);
	}
	
	public User getCreatorByProductId(int id) {
		List<User> user = jdbcTemplate.query("SELECT userId, userName, mailAddr FROM products NATURAL JOIN users WHERE productId = ?", USER_ROW_MAPPER, id);
		
		if (user.isEmpty())
			return null;
		
		return user.get(0);
	}

	public Product createProduct(String name, String description, String shortDescription, LocalDate uploadDate,
			byte[] logo, int creatorId) {

		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("productName", name);
		args.put("description", description);
		args.put("shortDescription", shortDescription);
		args.put("uploadDate", Date.valueOf(uploadDate));
		args.put("logo", logo);
		args.put("userId", creatorId);
		
		final Number productId = jdbcInsert.executeAndReturnKey(args);
		
		return new Product(productId.intValue(), name, description, shortDescription, uploadDate);
	}

	public byte[] getLogoByProductId(int productId) {
		byte[] logo = jdbcTemplate.queryForObject("SELECT logo FROM products WHERE productId = ?", byte[].class, productId);
		
		if (logo == null)
			return new byte[0];
		
		return logo;
	}

}
