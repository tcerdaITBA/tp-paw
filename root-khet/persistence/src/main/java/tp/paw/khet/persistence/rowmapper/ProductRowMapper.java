package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import org.springframework.jdbc.core.RowMapper;

import tp.paw.khet.Product;

public class ProductRowMapper implements RowMapper<Product> {

	private final static ProductRowMapper INSTANCE = new ProductRowMapper();
	
	public final static ProductRowMapper getInstance() {
		return INSTANCE;
	}
	
	private ProductRowMapper() {
	}
	
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		LocalDate date = rs.getTimestamp("uploadDate").toLocalDateTime().toLocalDate();
		return new Product(rs.getInt("productId"), rs.getString("productName"), 
				rs.getString("description"), rs.getString("shortDescription"), date);
	}
}
