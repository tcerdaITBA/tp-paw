package tp.paw.khet.webapp.dto;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import tp.paw.khet.model.FavList;
import tp.paw.khet.model.User;

public class CollectionListDTO {
	private List<CollectionDTO> collections;
	private int totalCount;
	private int count;
	
	public CollectionListDTO() {}
	
	public CollectionListDTO(final List<FavList> favLists, int totalCount, final URI baseURI, final Optional<User> user) {
		this.totalCount = totalCount;
		this.count = favLists.size();
		this.collections = new LinkedList<>();
		
		for (FavList f: favLists)
			collections.add(new CollectionDTO(f, baseURI, user));
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
