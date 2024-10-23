package ArrayKHeap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import ArrayKHeap.Exceptions.InvalidComparator;
import ArrayKHeap.Exceptions.InvalidInitialSize;
import ArrayKHeap.Exceptions.InvalidKArgument;

public class ArrayKHeap<T> {
    protected static final int defaultInitialSize = 32;
    protected static final int defaultK = 3;
    
    private final int increaseSizeFactor = 2;
    private T[] array;
    private final int k;
    private final Comparator<T> comparator;
    private int len;
    
    @SuppressWarnings("unchecked")
    protected ArrayKHeap(final Comparator<T> comparator, final int initialSize, final int k) {
        this.comparator = comparator;
        this.k = k;
        this.array = (T[])(new Object[initialSize]);
        this.len = 0;
    }

    protected static <T> void validateComparator(final Comparator<T> comparator) throws InvalidComparator {
        if(comparator == null) {
            throw new InvalidComparator();
        }
    }

    protected static void validateInitialSize(final int initialSize) throws InvalidInitialSize {
        if(initialSize <= 0) {
            throw new InvalidInitialSize();
        }
    }

    protected static void validateKArgument(final int k) throws InvalidKArgument {
        if(k <= 0) {
            throw new InvalidKArgument();
        }
    }

    private void increaseSize() {
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[])(new Object[array.length * increaseSizeFactor]);
        for(int i = 0; i < array.length; ++i) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    void swap(final int p, final int q) {
        final T tmp = array[p];
        array[p] = array[q];
        array[q] = tmp;
    }

    private void fixHeapUp(int p) {
        while(p != 0 && comparator.compare(array[p], array[(p - 1) / k]) > 0) {
            swap(p, (p - 1) / k);
            p = (p - 1) / k;
        }
    }

    private void fixHeapDown(int p) {
        int currentBest = p;
        do {
            swap(p, currentBest);
            p = currentBest;
            for(int i = p * k + 1; i <= p * k + k && i < len; ++i) {
                if(comparator.compare(array[currentBest], array[i]) < 0) {
                    currentBest = i;
                }
            }
        } while(p != currentBest);
    }

    public static <T> ArrayKHeap<T> newArrayKHeap(final Comparator<T> comparator) throws InvalidComparator {
        validateComparator(comparator);
        return new ArrayKHeap<T>(comparator, defaultInitialSize, defaultK);
    }

    public static <T> ArrayKHeap<T> newArrayKHeap(final Comparator<T> comparator, final int initialSize) throws InvalidInitialSize, InvalidComparator {
        validateComparator(comparator);
        validateInitialSize(initialSize);
        return new ArrayKHeap<T>(comparator, initialSize, defaultK);
    }

    public static <T> ArrayKHeap<T> newArrayKHeap(final Comparator<T> comparator, final int initialSize, final int k) throws InvalidInitialSize, InvalidKArgument, InvalidComparator {
        validateComparator(comparator);
        validateInitialSize(initialSize);
        validateKArgument(k);
        return new ArrayKHeap<T>(comparator, initialSize, k);
    }

    public void add(final T val) {
        if(len == this.array.length) {
            increaseSize();
        }

        array[len] = val;
        fixHeapUp(len++);
    }

    public final Optional<T> top() {
        return len != 0 ? Optional.of(array[0]) : Optional.empty();
    }

    public Optional<T> pop() {
        if(len == 0) {
            return Optional.empty();
        }
        swap(0, --len);
        fixHeapDown(0);
        
        final T ans = array[len];
        array[len] = null; // To destroy reference (in for e.g. int case it would be unnecessary)
        return Optional.of(ans);
    }

    public boolean clear() {
        if(len == 0) {
            return false;
        }
        for(int i = 0; i < len; ++i) {
            array[i] = null;
        }
        len = 0;
        return true;
    }

    public Optional<T> invertedTop() {
        if(len == 0) {
            return Optional.empty();
        }
        T ans = array[len - 1];
        for(int i = len - 2; i * k + 1 >= len; --i) {
            if(comparator.compare(array[i], ans) < 0) {
                ans = array[i];
            }
        }
        return Optional.of(ans);
    }
    
    @Override
    public String toString() {
        return "ArrayKHeap [array=" + Arrays.toString(array) + "]";
    }
}
