import java.util.Iterator;

public class Ex4<T> implements Iterator<T>, Iterable<T> {

    private Iterator<T> iter1;
    private Iterator<T> iter2;
    boolean first;

    public Ex4(final Iterator<T> iter1, final Iterator<T> iter2) {
        this. iter1 = iter1;
        this.iter2 = iter2;
        first = false;
    }
    
    @Override
    public boolean hasNext() {
        return iter1.hasNext() || iter2.hasNext();
    }

    @Override
    public T next() {
        first = !first;
        if((first || !iter2.hasNext()) && iter1.hasNext()) {
            return iter1.next();
        }
        return iter2.next();
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }
    
}
