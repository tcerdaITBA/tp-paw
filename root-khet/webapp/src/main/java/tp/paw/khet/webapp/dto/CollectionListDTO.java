package tp.paw.khet.webapp.dto;

import java.util.List;
import java.util.LinkedList;
import java.net.URI;

import tp.paw.khet.model.FavList;

public class CollectionListDTO {
	private List<CollectionDTO> collections;
	private int count;
	
	public CollectionListDTO() {};
	
	public CollectionListDTO(final List<FavList> favLists, final URI baseURI) {
		count = favLists.size();
		collections = new LinkedList<>();
		
		for (FavList f: favLists)
			collections.add(new CollectionDTO(f, baseURI));
	}

	public List<CollectionDTO> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionDTO> collections) {
		this.collections = collections;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
