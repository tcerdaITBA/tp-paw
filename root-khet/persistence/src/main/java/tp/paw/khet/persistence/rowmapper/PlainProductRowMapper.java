package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.interfaces.PlainProduct;

@Component
public class PlainProductRowMapper implements RowMapper<PlainProduct> {
	public PlainProduct mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		return Product.getBuilder(rs.getInt("productId"), rs.getString("productName"), rs.getString("shortDescription"))
				.category(rs.getString("category"))
				.build();
	}
}