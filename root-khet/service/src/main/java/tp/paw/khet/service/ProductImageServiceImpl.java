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
	
	@Override
	public ProductImage createProductImage(int imageId, int productId, byte[] data) {
		return productImageDao.createProductImage(imageId, productId, data);
	}

	@Override
	public List<Integer> getImagesIdByProductId(int productId) {
		return productImageDao.getImagesIdByProductId(productId);
	}

	@Override
	public ProductImage getImageByIds(int imageId, int productId) {
		return productImageDao.getImageByIds(imageId, productId);
	}

}