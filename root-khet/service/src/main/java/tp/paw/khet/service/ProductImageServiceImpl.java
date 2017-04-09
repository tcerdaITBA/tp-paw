package tp.paw.khet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.paw.khet.ProductImage;
import tp.paw.khet.persistence.ProductImageDao;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageDao productImageDao;
	
	public ProductImage createProductImage(int imageId, int productId, byte[] data) {
		return productImageDao.createProductImage(imageId, productId, data);
	}

	public List<Integer> getImagesIdByProductId(int productId) {
		return productImageDao.getImagesIdByProductId(productId);
	}

	public ProductImage getImageByIds(int imageId, int productId) {
		return productImageDao.getImageByIds(imageId, productId);
	}

}