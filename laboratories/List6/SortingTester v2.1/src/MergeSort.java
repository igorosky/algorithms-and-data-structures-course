import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import core.AbstractSortingAlgorithm;

public class MergeSort<T> extends AbstractSortingAlgorithm<T> {

    public MergeSort(final Comparator<? super T> comparator) {
        super(comparator);
    }
    
    @Override
    public List<T> sort(final List<T> list) {
        if(list.isEmpty()) {
            return new ArrayList<>();
        }

        final LinkedList<LinkedList<T>> toSort = new LinkedList<>();
        for(final T elem : list) {
            toSort.addLast(new LinkedList<T>(Arrays.asList(elem)));
        }

        int subsToSort = toSort.size();
        for(int i = 2; toSort.size() > 1; i += 2) {
            if(i > subsToSort) {
                toSort.addLast(toSort.removeFirst());
                subsToSort = toSort.size();
                i = 2;
            }
            final LinkedList<T> merged = new LinkedList<>();
            final LinkedList<T> first = toSort.removeFirst();
            final LinkedList<T> second = toSort.removeFirst();
            while(!first.isEmpty() || !second.isEmpty()) {
                if(!first.isEmpty() && !second.isEmpty()) {
                    if(compare(second.getFirst(), first.getFirst()) < 0) {
                        merged.add(second.removeFirst());
                    }
                    else {
                        merged.add(first.removeFirst());
                    }
                }
                else if(!first.isEmpty()) {
                    merged.addAll(first);
                    break;
                }
                else {
                    merged.addAll(second);
                    break;
                }
            }
            toSort.addLast(merged);
            if(i == subsToSort) {
                subsToSort = toSort.size();
                i = 0;
            }
        }
        return toSort.getFirst();
    }

}
