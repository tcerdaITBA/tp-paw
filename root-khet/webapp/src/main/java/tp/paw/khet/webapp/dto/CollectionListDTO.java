package tp.paw.khet.webapp.dto;

import java.util.List;
import java.util.LinkedList;
import java.net.URI;

import tp.paw.khet.model.FavList;

public class CollectionListDTO {
	//TODO: esto o UserDTO?
	private int ownerId;
	private List<CollectionDTO> collections;
	
	public CollectionListDTO() {};
	
	public CollectionListDTO(List<FavList> favLists, final URI baseURI, int ownerId) {
		this.ownerId = ownerId;
		collections = new LinkedList<>();
		for (FavList f: favLists) {
			collections.add(new CollectionDTO(f, baseURI));
		}
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public List<CollectionDTO> getCollections() {
		return collections;
	}

	public void setCollections(List<CollectionDTO> collections) {
		this.collections = collections;
	}
	
	
}
