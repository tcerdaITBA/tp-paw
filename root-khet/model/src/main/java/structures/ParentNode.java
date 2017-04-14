package structures;

import java.util.ArrayList;
import java.util.List;

import tp.paw.khet.User;

public class ParentNode<T> {
	
	private T parent;
	
	private List<T> childs;
	
	public ParentNode(T parent) {
		this.parent = parent;
		this.childs = new ArrayList<T>();
	}
	
	public ParentNode(T parent, List<T> childs) {
		this.parent = parent;
		this.childs = childs;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public List<T> getChilds() {
		return childs;
	}

	public void setChilds(List<T> childs) {
		this.childs = childs;
	}

	public void addChild(T child) {
		this.childs.add(child);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childs == null) ? 0 : childs.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof ParentNode))
			return false;
		
		@SuppressWarnings("unchecked")
		ParentNode<T> other = (ParentNode<T>) obj;
		if (childs == null) {
			if (other.childs != null)
				return false;
		} else if (!childs.equals(other.childs))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}

}
