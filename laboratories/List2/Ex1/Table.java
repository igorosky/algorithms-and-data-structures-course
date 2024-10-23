package Ex1;
import java.util.Iterator;

public class Table<T> implements Iterable<T> {

    private T[] table;

    @SuppressWarnings("unchecked")
    public Table(final int size) {
        table = (T[])new Object[size];
    }

    public T at(final int p) {
        return table[p];
    }

    public void set(final int p, final T val) {
        table[p] = val;
    }

    public int size() {
        return table.length;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new TableIterator<T>(this);
    }
}
