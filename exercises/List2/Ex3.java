import java.util.Iterator;

public class Ex3 implements Iterator<Integer>, Iterable<Integer> {

    private int p;
    private int q;

    public Ex3() {
        p = 1;
        q = 0;
    }

    @Override
    public boolean hasNext() {
        // Not sure if sum can overflow in Java but if it can it will eventually return false
        return p + q > 0;
    }

    @Override
    public Integer next() {
        int ans = p + q;
        q = p;
        p = ans;
        return ans;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
    
}
