package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import BST.BST;

public class BSTTest {
    private final Comparator<Integer> cmp = new Comparator<Integer>() {
        
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
        
    };
    BST<Integer> bst;
    private final HashSet<Integer> containedElements = new HashSet<>();
    final Random rand = new Random(System.currentTimeMillis());

    @Before
    public void containerClear() {
        containedElements.clear();
        bst = new BST<>(cmp);
    }

    private void insert(final Integer value) {
        final boolean wasAbsent = containedElements.add(value);
        assertTrue(bst.find(value).isEmpty() == wasAbsent);
        assertTrue(bst.insert(value) == wasAbsent);
        assertTrue(bst.find(value).isPresent());
    }

    private void remove(final Integer value) {
        final boolean wasAbsent = containedElements.remove(value);
        assertTrue(bst.find(value).isPresent() == wasAbsent);
        assertTrue(bst.remove(value) == wasAbsent);
        assertTrue(bst.find(value).isEmpty());
    }

    private void validate() {
        for(final Integer i : bst) {
            assertTrue(containedElements.contains(i));
        }
        for(final Integer i : containedElements) {
            assertTrue(bst.find(i).isPresent());
        }
    }

    private void validateLessThan() {
        if(containedElements.isEmpty()) {
            assertEquals(0, bst.lowerThan(Integer.MIN_VALUE).size());
            return;    
        }
        final List<Integer> validList = new ArrayList<>();
        for(final Integer val : containedElements) {
            validList.add(val);
        }
        validList.sort(cmp);
        final int lessThanIdx = rand.nextInt(validList.size());
        final int lessThan = validList.get(lessThanIdx);
        final List<Integer> list = bst.lowerThan(lessThan);
        assertEquals(lessThanIdx, list.size());
        for(int i = 0; i < lessThanIdx; ++i) {
            final Integer val = list.get(i);
            assertEquals(validList.get(i), val);
            assertTrue(val < lessThan);
        }
    }

    public void checkIterator(final int[] ans) {
        int p = 0;
        for(final Integer i : bst) {
            assertTrue(p < ans.length);
            assertEquals(ans[p++], (int)i);
        }
        assertEquals(ans.length, p);
    }

    @Test
    public void basicTest() {
        assertTrue(bst.findNext(5).isEmpty());
        
        insert(5);
        assertTrue(bst.findNext(5).isEmpty());
        
        insert(2);
        assertTrue(bst.findNext(5).isEmpty());
        assertEquals(Optional.of(5), bst.findNext(2));
        
        insert(7);
        assertEquals(Optional.of(7), bst.findNext(5));
        
        insert(5);
        insert(2);
        insert(7);
        validate();
        
        checkIterator(new int[]{5, 2, 7});

        assertEquals(Optional.of(2), bst.minimum());
        assertEquals(Optional.of(7), bst.maximum());

        insert(1);
        insert(3);
        insert(8);
        insert(6);
        validate();

        insert(1);
        insert(3);
        insert(8);
        insert(6);
        validate();

        checkIterator(new int[]{5, 2, 1, 3, 7, 6, 8});

        remove(10);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));

        remove(8);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));

        remove(7);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));

        remove(5);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));
        remove(5);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));

        remove(5);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));
        remove(5);
        validate();
        assertEquals(Optional.of(6), bst.findNext(5));

        assertEquals(Optional.of(6), bst.findNext(5));
        assertEquals(Optional.of(1), bst.minimum());
        assertEquals(Optional.of(6), bst.maximum());
    }

    @Test
    public void randomTests() {
        for(int i = 0; i < 1000000; ++i) {
            final int n = rand.nextInt(1000);
            if(rand.nextBoolean()) {
                insert(n);
            }
            else {
                remove(n);
            }
            if(i % 100 == 0) {
                validate();
            }
            if(i % 10000 == 0) {
                validateLessThan();
            }
        }
    }
}
