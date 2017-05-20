package tp.paw.khet.service;

import java.util.ArrayList;
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
	public ProductImage createProductImage(final int imageId, final int productId, final byte[] data) {
		return productImageDao.createProductImage(imageId, productId, data);
	}
	
	@Override
	public List<ProductImage> createProductImages(final List<byte[]> imageBytes, final int productId) {
		List<ProductImage> productImages = new ArrayList<>();
		for (int i = 0; i < imageBytes.size(); i++)
			productImages.add(createProductImage(i, productId, imageBytes.get(i)));
		
		return productImages;
	}

	@Override
	public List<Integer> getImagesIdByProductId(final int productId) {
		return productImageDao.getImagesIdByProductId(productId);
	}

	@Override
	public ProductImage getImageByIds(final int imageId, final int productId) {
		return productImageDao.getImageByIds(imageId, productId);
	}


}