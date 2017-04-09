package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Product;

@Component
public class ProductRowMapper implements RowMapper<Product> {
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Product(rs.getInt("productId"), rs.getString("productName"), 
				rs.getString("description"), rs.getString("shortDescription"), rs.getTimestamp("uploadDate").toLocalDateTime());
	}
}
