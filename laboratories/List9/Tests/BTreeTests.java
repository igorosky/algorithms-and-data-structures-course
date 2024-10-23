package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import BTree.BTree;

public class BTreeTests {
    private HashSet<Integer> hashSet;
    private BTree<Integer> btree;
    private Random rand = new Random(System.currentTimeMillis());
    private Comparator<Integer> cmp = new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
        
    };

    private void add(final int val) {
        assertEquals(hashSet.add(val), btree.add(val));
    }

    private void validate() {
        assertEquals(hashSet.size(), btree.size());
        for(final Integer val : hashSet) {
            assertTrue(btree.find(val).isPresent());
        }
    }

    private void remove(final int val) {
        assertEquals(hashSet.remove(val), btree.remove(val).isPresent());
    }
    
    @Before
    public void beforeEach() {
        btree = new BTree<Integer>(4, cmp);
        hashSet = new HashSet<>();
    }

    @Test
    public void basicTest() {
        assertTrue(btree.add(12));
        assertTrue(btree.add(15));
        assertTrue(btree.add(13));
        assertTrue(!btree.add(12));
        assertTrue(btree.add(17));
        assertTrue(btree.add(14));
        assertTrue(btree.add(11));
        assertTrue(!btree.add(12));
        assertTrue(btree.add(20));
        assertTrue(btree.add(21));
        assertTrue(btree.add(22));
        assertTrue(btree.add(24));
        assertTrue(btree.add(27));
        assertTrue(btree.add(25));
        assertTrue(btree.add(1));
        assertTrue(btree.add(3));
        assertTrue(btree.add(2));
        assertTrue(btree.add(5));
    }

    @Test
    public void randomTest() {
        for(int testCase = 0; testCase < 100; ++testCase) {
            beforeEach();
            btree = new BTree<Integer>(rand.nextInt(10) + 2, cmp);
            final int testSize = rand.nextInt(900001) + 100000;
            for(int testPart = 0; testPart < testSize; ++testPart) {
                if(rand.nextBoolean()) {
                    add(rand.nextInt(500));
                }
                else {
                    remove(rand.nextInt(500));
                }
                if(testPart % 10000 == 0) {
                    validate();
                }
            }
        }
    }
}
