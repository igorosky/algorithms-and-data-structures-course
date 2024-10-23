package ArrayKHeap;
import java.util.Comparator;

import ArrayKHeap.Exceptions.InvalidComparator;
import ArrayKHeap.Exceptions.InvalidInitialSize;

public class Array3Heap<T> extends ArrayKHeap<T> {
    private Array3Heap(final Comparator<T> comparator, int initialSize) {
        super(comparator, initialSize, 3);
    }
    
    public static <T> Array3Heap<T> newArray3Heap(final Comparator<T> comparator) throws InvalidComparator {
        validateComparator(comparator);
        return new Array3Heap<T>(comparator, defaultInitialSize);
    }

    public static <T> Array3Heap<T> newArray3Heap(final Comparator<T> comparator, final int initialSize) throws InvalidComparator, InvalidInitialSize {
        validateComparator(comparator);
        validateInitialSize(initialSize);
        return new Array3Heap<T>(comparator, initialSize);
    }
}
