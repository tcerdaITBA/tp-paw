package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Product;

@Component
public class ProductRowMapper implements RowMapper<Product> {
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Product.getBuilder()
				.id(rs.getInt("productId"))
				.name(rs.getString("productName"))
				.description(rs.getString("description"))
				.shortDescription(rs.getString("shortDescription"))
				.website(rs.getString("website"))
				.category(rs.getString("category"))
				.uploadDate(rs.getTimestamp("uploadDate").toLocalDateTime())
				.build();
	}
}
