import java.util.Comparator;
import java.util.List;

import core.AbstractSwappingSortingAlgorithm;

public class SelectionSort<T> extends AbstractSwappingSortingAlgorithm<T> {

    public SelectionSort(final Comparator<? super T> comparator) {
        super(comparator);
    }
    
    @Override
    public List<T> sort(final List<T> list) {
        for(int i = 0; i < list.size() / 2; ++i) {
            final int last = list.size() - i - 1;
            
            T min = list.get(i);
            int minIdx = i;
            T max = list.get(last);
            int maxIdx = last;
            if(compare(min, max) > 0) {
                T tmp = min;
                min = max;
                max = tmp;
                minIdx += maxIdx;
                maxIdx -= minIdx;
                maxIdx *= -1;
                minIdx -= maxIdx;
            }
            for(int j = i + 1; j < last; ++j) {
                final T element = list.get(j);
                if(compare(min, element) > 0) {
                    min = element;
                    minIdx = j;
                }
                else if(compare(max, element) < 0) {
                    max = element;
                    maxIdx = j;
                }
            }
            swap(list, i, minIdx);
            swap(list, last, (maxIdx != i ? maxIdx : minIdx));
        }
        return list;
    }
    
}
