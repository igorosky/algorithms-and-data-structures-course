package Ex2;

import java.util.Iterator;
import java.util.Optional;

public class PascalIterator implements Iterator<Integer>, Iterable<Integer> {
    final int n;
    int i;

    // This is not necessary - but it reduces time complexity (for a cost of space complexity)
    final int[] previousRow;
    
    private PascalIterator(final int n) {
        this.n = n;
        this.i = 0;
        previousRow = new int[n + 1];
        previousRow[0] = n == 1 ? 1 : 0;
        if(n > 1) {
            final PascalIterator prevRow = new PascalIterator(n - 1);
            for(int i = 1; i < n; ++i) {
                previousRow[i] = prevRow.next();
            }
        }
    }

    public static Optional<PascalIterator> newPascalIterator(final int n) {
        if(n <= 0) {
            return Optional.empty();
        }
        return Optional.of(new PascalIterator(n));
    }

    @Override
    public boolean hasNext() {
        return i < n;
    }

    @Override
    public Integer next() {
        return previousRow[i] + previousRow[++i];
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
}
