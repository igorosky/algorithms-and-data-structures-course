import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
// import java.util.ListIterator;
// import java.util.Optional;
import java.util.Random;

import core.AbstractSwappingSortingAlgorithm;

public class QuickSort<T> extends AbstractSwappingSortingAlgorithm<T> {
    public static boolean randomPivot = false;
    private Random rand;

    // private class ListIteratorWrapper implements Cloneable {
    //     private final ListIterator<T> iter;
    //     private final List<T> list;
    //     private int index;
    //     private Optional<T> current;

    //     public ListIteratorWrapper(final List<T> list, final int index) {
    //         this.list = list;
    //         this.iter = list.listIterator(index);
    //         this.current = Optional.of(iter.next());
    //         this.index = index;
    //     }

    //     public boolean hasNext() {
    //         return iter.hasNext();
    //     }

    //     public boolean hasPrevious() {
    //         return iter.hasPrevious();
    //     }

    //     public T next() {
    //         ++index;
    //         final T ans = iter.next();
    //         current = Optional.of(ans);
    //         return ans;
    //     }

    //     public T previous() {
    //         --index;
    //         final T ans = iter.previous();
    //         current = Optional.of(ans);
    //         return ans;
    //     }

    //     public int getIndex() {
    //         return index;
    //     }

    //     public void set(final T val) {
    //         iter.set(val);
    //         current = Optional.of(val);
    //     }

    //     public T get() {
    //         return current.orElseThrow(() -> new IllegalStateException("No value to return"));
    //     }

    //     @Override
    //     protected ListIteratorWrapper clone() {
    //         final ListIteratorWrapper ans = new ListIteratorWrapper(list, index);
    //         return ans;
    //     }

    //     public void swap(final ListIteratorWrapper other) {
    //         final T tmp = get();
    //         set(other.get());
    //         other.set(tmp);
    //     }
    // }

    public QuickSort(final Comparator<? super T> comparator) {
        super(comparator);
        rand = new Random(System.currentTimeMillis());
    }

    private int getPivot(final int p, final int q) {
    // private ListIteratorWrapper getPivot(final ListIteratorWrapper p, final ListIteratorWrapper q) {
    //     if(randomPivot) {
    //         final int pivotOffset = rand.nextInt(q.getIndex() - p.getIndex());
    //         for(int i = 0; i < pivotOffset; ++i) {
    //             p.next();
    //         }
    //         final ListIteratorWrapper ans = p.clone();
    //         for(int i = 0; i < pivotOffset; ++i) {
    //             p.previous();
    //         }
    //         return ans;
    //     }
    //     return p.clone();
        return randomPivot && q - p > 0 ? p + rand.nextInt(q - p) : p;
    }

    // private void sort(final ListIteratorWrapper first, final ListIteratorWrapper last) {
    //     if(last.getIndex() - first.getIndex() < 1) {
    //         return;
    //     }
    //     final ListIteratorWrapper q = last.clone();
    //     ListIteratorWrapper pivot = getPivot(first, last);
    //     pivot.swap(q);
    //     pivot = q.clone();
    //     q.previous();
    //     final ListIteratorWrapper p = first.clone();
    //     while(true) {
    //         while(compare(p.get(), pivot.get()) <= 0 && p.getIndex() < last.getIndex()) {
    //             p.next();
    //         }
    //         while(compare(q.get(), pivot.get()) >= 0 && q.getIndex() > first.getIndex()) {
    //             q.previous();
    //         }
    //         if(p.getIndex() >= q.getIndex()) {
    //             break;
    //         }
    //         p.swap(q);
    //     }
    //     p.swap(pivot);
    //     if(p.getIndex() - 1 > first.getIndex()) {
    //         p.previous();
    //         sort(first, p);
    //         p.next();
    //     }
    //     if(p.getIndex() + 1 < last.getIndex()) {
    //         p.next();
    //         sort(p, last);
    //     }
    // }

    private class ToSort {
        private final int p;
        private final int q;

        private ToSort(final int p, final int q) {
            this.p = p;
            this.q = q;
        }
    }
    
    @Override
    public List<T> sort(final List<T> list) {
        if(list.size() <= 1) {
            return list;
        }

        final ArrayList<T> cheatList = new ArrayList<>();
        for(final T v : list) {
            cheatList.add(v);
        }
        
        // final ListIteratorWrapper first = new ListIteratorWrapper(cheatList, 0);
        // final ListIteratorWrapper last = new ListIteratorWrapper(cheatList, cheatList.size() - 1);
        // sort(first, last);

        final LinkedList<ToSort> toSort = new LinkedList<>();
        toSort.add(new ToSort(0, cheatList.size() - 1));

        while(!toSort.isEmpty()) {
            final ToSort current = toSort.removeFirst();
            int pivotIdx = getPivot(current.p, current.q);
            final T pivot = cheatList.get(pivotIdx);
            swap(cheatList, pivotIdx, current.q);
            int p = current.p;
            int q = current.q - 1;
            while(true) {
                while(p < current.q && compare(cheatList.get(p), pivot) <= 0) {
                    ++p;
                }
                while(q > 0 && compare(cheatList.get(q), pivot) >= 0) {
                    --q;
                }
                if(p >= q) {
                    break;
                }
                swap(cheatList, p++, q--);
            }
            swap(cheatList, p, current.q);
            if(p - 1 > current.p) {
                toSort.add(new ToSort(current.p, p - 1));
            }
            if(p + 1 < current.q) {
                toSort.add(new ToSort(p + 1, current.q));
            }
        }

        return cheatList;
    }
}
