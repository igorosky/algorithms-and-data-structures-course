package Ex1;
import java.util.Iterator;

public class TableIterator<T> implements Iterator<T> {

    private final Table<T> table;
    private int idx;
    
    public TableIterator(final Table<T> table) {
        this.table = table;
        idx = 0;
    }
    
    @Override
    public boolean hasNext() {
        return idx < table.size();
    }

    @Override
    public T next() {
        return table.at(idx++);
    }
    
}
