package tp.paw.khet.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import tp.paw.khet.model.ProductImage;

@Repository
public class ProductImageHibernateDao implements ProductImageDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ProductImage createProductImage(final int imageId, final int productId, final byte[] data) {
		final ProductImage productImage = new ProductImage(imageId, productId, data);

		em.persist(productImage);
		
		return productImage;
	}

	@Override
	public List<Integer> getImagesIdByProductId(final int productId) {
		final TypedQuery<Integer> query = em.createQuery("select productImageId from ProductImage as pi where pi.productId = :productId "
														+ "ORDER BY pi.productImageId", Integer.class);
		query.setParameter("productId", productId);
		
		return query.getResultList();
	}

	@Override
	public ProductImage getImageByIds(int imageId, int productId) {
		return em.find(ProductImage.class, new ProductImage.ProductImagePrimaryKeyIds(imageId, productId));
	}

}
