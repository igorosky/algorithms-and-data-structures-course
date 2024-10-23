package Mod;

import java.util.Iterator;

public class Mod implements Iterable<Integer>, Iterator<Integer> {

    final private int n;
    private int m;
    
    public Mod(final int n) {
        this.n = n;
        m = n;
    }

    @Override
    public boolean hasNext() {
        return m > 0;
    }

    @Override
    public Integer next() {
        final int ans = m--;
        while(m > 0 && n % m != 0) {
            --m;
        }
        return ans;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
    
}
