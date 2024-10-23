import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

import BTree.BTree;

public class Main {
    private final static HashSet<Integer> hashSet = new HashSet<>();
    private final static BTree<Integer> btree = new BTree<Integer>(4, new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
        
    });
    
    private static void add(final int val) {
        assert hashSet.add(val) == btree.add(val);
    }

    private static void validate() {
        assert hashSet.size() == btree.size();
        for(final Integer val : hashSet) {
            if(btree.find(val).isEmpty()) {
                btree.print();
                assert false;
            }
        }
    }

    private static void remove(final int val) {
        assert hashSet.remove(val) == btree.remove(val).isPresent();
    }

    public static void main(String[] args) {
        btree.add(12);
        btree.add(15);
        btree.add(13);
        btree.add(12);
        btree.add(17);
        btree.add(14);
        btree.add(11);
        btree.add(12);
        btree.add(20);
        btree.add(1);
        btree.add(5);
        btree.add(7);
        btree.add(6);
        btree.add(3);
        btree.add(4);
        btree.add(0);
        btree.add(-5);
        btree.add(21);
        btree.add(23);
        btree.add(22);
        btree.add(70);
        btree.add(69);
        btree.add(50);
        
        btree.add(12);
        btree.add(15);
        btree.add(13);
        btree.add(12);
        btree.add(17);
        btree.add(14);
        assert btree.remove(12).isPresent();
        assert btree.find(13).isPresent();
        assert btree.remove(13).isPresent();
        assert btree.remove(17).isPresent();
        assert btree.remove(14).isPresent();
        assert btree.remove(11).isPresent();

        
        for(int i = 0; i < 1000000; ++i) {
            btree.clear();
            hashSet.clear();
    
            final Random rand = new Random(System.currentTimeMillis());
            
            final int testSize = rand.nextInt(90) + 10;
            for(int testPart = 0; testPart < testSize; ++testPart) {
                if(rand.nextBoolean()) {
                    add(rand.nextInt(100));
                }
                else {
                    remove(rand.nextInt(100));
                }
    
                validate();
            }
        }
    }
}
