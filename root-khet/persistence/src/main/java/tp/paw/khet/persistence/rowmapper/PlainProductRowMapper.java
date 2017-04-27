package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Product;

@Component
public class PlainProductRowMapper implements RowMapper<Product> {
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Product.getBuilder(rs.getInt("productId"), rs.getString("productName"), rs.getString("shortDescription"))
				.category(rs.getString("category"))
				.build();
	}
}