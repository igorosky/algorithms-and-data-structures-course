import java.util.Iterator;

public class Ex7<T> implements Iterable<T> {

    private final T[][] arr;
    

    public Ex7(final T[][] arr) {
        this.arr = arr;
    }

    class Iter implements Iterator<T> {
        
        private final T[][] arr;
        private int row;
        private int col;

        public Iter(final T[][] arr) {
            this.arr = arr;
            row = 0;
            col = 0;
        }

        @Override
        public boolean hasNext() {
            return row < arr.length;
        }

        @Override
        public T next() {
            final T ans = arr[row][col];
            if(++col >= arr[row].length) {
                ++row;
                col = 0;
            }
            return ans;
        }

    }

    class ReversedIter implements Iterator<T> {

        private final T[][] arr;
        private int row;
        private int col;

        public ReversedIter(final T[][] arr) {
            this.arr = arr;
            row = arr.length - 1;
            if(row >= 0) {
                col = arr[row].length;
            }
        }
        
        @Override
        public boolean hasNext() {
            return row >= 0;
        }

        @Override
        public T next() {
            final T ans = arr[row][col];
            if(--col < 0) {
                --row;
                if(row >= 0) {
                    col = arr[row].length - 1;
                }
            }
            return ans;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(arr);
    }

    public Iterator<T> reversedIterator() {
        return new ReversedIter(arr);
    }

}
