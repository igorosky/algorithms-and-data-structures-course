import java.util.Iterator;

public class Ex5 implements Iterator<Integer>, Iterable<Integer> {
    
    private final int upperBond;
    private int nextPrime;

    static public boolean isPrime(final int p) {
        for(final int n : new Ex5((int)Math.sqrt((double)p))) {
            if(p % n == 0) {
                return false;
            }
        }
        return true;
    }
    
    public Ex5(final int upperBond) {
        this.upperBond = upperBond;
        this.nextPrime = 2;
    }

    @Override
    public boolean hasNext() {
        return nextPrime < upperBond;
    }

    @Override
    public Integer next() {
        final int ans = nextPrime;
        if(nextPrime == 2) {
            nextPrime = 3;
        }
        else do {
            nextPrime += 2;
        } while(nextPrime < upperBond && !isPrime(nextPrime));
        return ans;
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }
    
}
