import java.util.Comparator;
import java.util.List;

import core.AbstractSwappingSortingAlgorithm;

public class ShakerSort<T> extends AbstractSwappingSortingAlgorithm<T> {
    private int sortedMargin;

    public ShakerSort(final Comparator<? super T> comparator) {
        super(comparator);
    }

    private void bubble(final List<T> list, int i, final boolean up) {
        final int change = up ? 1 : -1;
        for(; up ? i < (list.size() - sortedMargin - 1) : i > sortedMargin; i += change) {
            if(compare(list.get(i), list.get(i + change)) * change > 0) {
                swap(list, i, i + change);
            }
        }
    }

    @Override
    public List<T> sort(final List<T> list) {
        sortedMargin = 0;
        for(; sortedMargin < list.size() / 2; ++sortedMargin) {
            bubble(list, sortedMargin, true);
            bubble(list, list.size() - sortedMargin - 1, false);
        }
        return list;
    }
    
}
