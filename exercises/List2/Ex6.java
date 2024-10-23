import java.util.Iterator;

// Java does not allow to allocate array of a generic type so it cannot be generic
public class Ex6 implements Iterable<Integer>, Iterator<Integer> {
    
    private int[] arr;
    private int i;

    public Ex6(int[] arr) {
        this.arr = arr;
        i = 0;
    }

    @Override
    public boolean hasNext() {
        return i < arr.length;
    }

    @Override
    public Integer next() {
        return arr[i++];
    }

    /**
     * Removes element that now would be returned if next were called (also return it)
     * WARN! It actually does not remove element it just skips it because it is impossible to implement remove on array (on ArrayList or Vector or List etc. is possible)
     */
    @Override
    public void remove() {
        int[] newArr = new int[arr.length - 1];
        for(int j = 0; j < arr.length; ++j) {
            if(j == i) {
                continue;
            }
            final int p = j + (j > i ? 1 : 0);
            newArr[p] = arr[i];
            arr = newArr;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
    
}
