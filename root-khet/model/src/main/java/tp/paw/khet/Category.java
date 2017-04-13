package tp.paw.khet;

import java.util.HashMap;
import java.util.Map;

public enum Category {
	APP,
	ART,
	BOOK,
	FASHION,
	FILM,
	FOOD,
	GADGET,
	GAME,
	MUSIC,
	OTHER;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public static Map<Category,String> getCategories(){
		Map<Category,String> map = new HashMap<>();
		for(Category category:Category.values())
			map.put(category, category.toString());
		
		return map;	
	}
}
