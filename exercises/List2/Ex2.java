import java.util.Iterator;

public class Ex2 implements Iterator<Integer>, Iterable<Integer> {

    private int _current = 0;

    public Ex2(final int first) {
        _current = first;
    }

    @Override
    public boolean hasNext() {
        return _current != Integer.MAX_VALUE;
    }

    @Override
    public Integer next() {
        return _current++;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
    
}
