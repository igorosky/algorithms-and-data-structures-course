import java.util.Iterator;

public class Ex1<T> implements Iterator<T>, Iterable<T> {

    private final int k;
    private Iterator<T> iter;
    
    public Ex1(Iterator<T> iter, final int k) {
        this.iter = iter;
        this.k = k;
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }

    @Override
    public T next() {
        final T ans = iter.next();
        for(int i = 1; i < k && iter.hasNext(); ++i) {
            iter.next();
        }
        return ans;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }
    
}
