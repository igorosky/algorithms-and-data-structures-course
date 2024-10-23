import java.util.Comparator;
import java.util.Optional;

import ArrayKHeap.Array3Heap;
import ArrayKHeap.Exceptions.InvalidComparator;

public class Main {
    public static void main(String[] args) {
        final Array3Heap<Integer> heap;
        try {
            heap = Array3Heap.newArray3Heap(new Comparator<Integer>() {
    
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
                
            });
        }
        catch(final InvalidComparator e) {
            System.out.println(e.getMessage());
            return;
        }
            
    
        heap.add(4);
        heap.add(2);
        heap.add(3);
        heap.add(1);
        Optional<Integer> val = heap.pop();
        while(val.isPresent()) {
            System.out.println(val.get());
            val = heap.pop();
        }
    }
}
