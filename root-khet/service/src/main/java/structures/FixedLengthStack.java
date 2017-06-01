package structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.Vector;

@SuppressWarnings("serial")
public class FixedLengthStack<T> extends Stack<T> {
    private int maxSize;

    public FixedLengthStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        //If the stack is too big, remove elements until it's the right size.
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        
        return super.push(object);
    }
    
    @Override
    public synchronized Iterator<T> iterator() {
        return new FIFOIterator<>(this);
    }
    
    private class FIFOIterator<E> implements Iterator<E> {
        int index;
        Vector<E> vector;
        
        public FIFOIterator(Vector<E> vector) {
            this.vector = vector;
            index = vector.size() - 1;
        }
        
        @Override
        public boolean hasNext() {
            return 0 <= index && index < vector.size();
        }

        @Override
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return vector.get(index--);
        }
        
    }
}
