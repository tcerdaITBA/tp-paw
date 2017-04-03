package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.ProductImage;
import tp.paw.khet.persistence.ProductImageDao;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	private ProductImageDao productImageDao;

	@Autowired
	public ProductImageServiceImpl(ProductImageDao productImageDao) {
		this.productImageDao = productImageDao;
	}
	
	public List<ProductImage> getImagesByProductId(int productId) {
		return productImageDao.getImagesByProductId(productId);
	}

	public ProductImage createProductImage(int imageId, int productId, byte[] data) {
		return productImageDao.createProductImage(imageId, productId, data);
	}

}
