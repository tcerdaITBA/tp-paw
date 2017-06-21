package tp.paw.khet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.paw.khet.model.Product;
import tp.paw.khet.model.ProductImage;
import tp.paw.khet.persistence.ProductImageDao;

@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	private ProductImageDao productImageDao;

	@Transactional
	@Override
	public ProductImage createProductImage(final int imageId, final int productId, final byte[] data) {
		return productImageDao.createProductImage(imageId, productId, data);
	}

	@Transactional
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
	public List<Integer> getImagesIdsFromProduct(final Product product) {
		final List<Integer> idList = new ArrayList<>();

		final List<ProductImage> list = product.getImages();

		for (final ProductImage image : list)
			idList.add(image.getProductImageId());

		return Collections.unmodifiableList(idList);
	}

	@Override
	public ProductImage getImageByIds(final int imageId, final int productId) {
		return productImageDao.getImageByIds(imageId, productId);
	}
}