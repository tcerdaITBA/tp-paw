package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.Product;

@Component
public class FullProductRowMapper implements RowMapper<Product> {
	
	@Autowired
	private UserRowMapper userRowMapper;
	
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Product.getBuilder(rs.getInt("productId"), rs.getString("productName"), rs.getString("shortDescription"))
				.description(rs.getString("description"))
				.website(rs.getString("website"))
				.category(rs.getString("category"))
				.uploadDate(rs.getTimestamp("uploadDate").toLocalDateTime())
				.creator(userRowMapper.mapRow(rs, rowNum))
				.build();
	}
}
