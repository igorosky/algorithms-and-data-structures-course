import java.util.Comparator;
import java.util.List;

import core.AbstractSwappingSortingAlgorithm;

public class InsertionSort<T> extends AbstractSwappingSortingAlgorithm<T> {

	public InsertionSort(final Comparator<? super T> comparator) {
		super(comparator);
	}

    private int binSearch(final List<T> list, final T val, int q) {
        int p = 0;
        while(p < q) {
            final int s = (p + q) / 2;
            if(compare(list.get(s), val) > 0) {
                q = s;
            }
            else {
                p = s + 1;
            }
        }
        return p;
    }

    private void insert(final List<T> list, int p, int q) {
        while(p < q) {
            swap(list, q - 1, q);
            --q;
        }
    }

    @Override
    public List<T> sort(final List<T> list) {
        for(int i = 1; i < list.size(); ++i) {
            insert(list, (binSearch(list, list.get(i), i)), i);
        }

        return list;
    }
    
}
