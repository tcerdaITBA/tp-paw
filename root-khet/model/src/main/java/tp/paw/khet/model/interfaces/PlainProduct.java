package tp.paw.khet.model.interfaces;

import tp.paw.khet.model.Category;

/**
 * Represents a product with only essential information: id, name, category and short description.
 */
public interface PlainProduct {
	public int getId();
	public String getName();
	public Category getCategory();
	public String getShortDescription();
}
