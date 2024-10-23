package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;

import ArrayKHeap.ArrayKHeap;
import ArrayKHeap.Exceptions.InvalidComparator;
import ArrayKHeap.Exceptions.InvalidInitialSize;
import ArrayKHeap.Exceptions.InvalidKArgument;

public class ArrayKHeapTest {
    private final Comparator<Integer> cmp = new Comparator<Integer>() {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
        
    };
    private final Random rand = new Random(System.currentTimeMillis());

    @Test
    public void initializationTest() {
        assertThrows(InvalidComparator.class, () -> ArrayKHeap.newArrayKHeap(null));
        try {
            ArrayKHeap.newArrayKHeap(cmp);
        }
        catch(final Exception e) {
            assertTrue(false);
        }

        assertThrows(InvalidInitialSize.class, () -> ArrayKHeap.newArrayKHeap(cmp, 0));
        assertThrows(InvalidInitialSize.class, () -> ArrayKHeap.newArrayKHeap(cmp, -1));
        try {
            ArrayKHeap.newArrayKHeap(cmp, 1);
        }
        catch(final Exception e) {
            assertTrue(false);
        }

        assertThrows(InvalidKArgument.class, () -> ArrayKHeap.newArrayKHeap(cmp, 1, 0));
        assertThrows(InvalidKArgument.class, () -> ArrayKHeap.newArrayKHeap(cmp, 1, -1));
        try {
            ArrayKHeap.newArrayKHeap(cmp, 1, 1);
        }
        catch(final Exception e) {
            assertTrue(false);
        }
    }

    private static <T> void validateHeapWithList(final Comparator<T> comparator, final List<T> list, final ArrayKHeap<T> heap) {
        heap.clear();
        for(final T v : list) {
            heap.add(v);
        }
        list.sort(comparator);
        for(int i = list.size() - 1; i >= 0; --i) {
            final Optional<T> topOpt = heap.top();
            final Optional<T> popOpt = heap.pop();
            assertTrue(topOpt.isPresent());
            assertTrue(popOpt.isPresent());
            final T top = topOpt.get();
            final T pop = popOpt.get();
            assertEquals(top, pop);
            assertEquals(list.get(i), pop);
        }
        assertFalse(heap.clear());
    }

    private static <T> void validateHeapWithList(final Comparator<T> comparator, final List<T> list) {
        final ArrayKHeap<T> heap;
        try {
            heap = ArrayKHeap.newArrayKHeap(comparator);
        } catch (InvalidComparator e) {
            assertTrue(false);
            return;
        }
        validateHeapWithList(comparator, list, heap);
    }

    private void validateHeapWithList(final List<Integer> list, final ArrayKHeap<Integer> heap) {
        validateHeapWithList(cmp, list, heap);
    }

    private void validateHeapWithList(final List<Integer> list) {
        validateHeapWithList(cmp, list);
    }
    
    @Test
    public void basicTest() {
        final int size = rand.nextInt(1000000);
        ArrayList<Integer> array = new ArrayList<>();
        for(int i = 0; i < size; ++i) {
            array.add(rand.nextInt(Integer.MAX_VALUE));
        }
        validateHeapWithList(array);
    }

    @Test
    public void randomTests() {
        for(int testNo = 0; testNo < 10000; ++testNo) {
            final int initialSize = rand.nextInt(1000) - 100;
            final int k = rand.nextInt(101) - 1;
            final ArrayKHeap<Integer> heap;
            try {
                heap = ArrayKHeap.newArrayKHeap(cmp, initialSize, k);
            }
            catch (final InvalidComparator e) {
                assertTrue(false);
                return;
            }
            catch (final InvalidInitialSize e) {
                assertTrue(initialSize <= 0);
                continue;
            }
            catch (final InvalidKArgument e) {
                assertTrue(k <= 0);
                continue;
            }
            final int testSize = rand.nextInt(1000);
            final ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0; i < testSize; ++i) {
                list.add(rand.nextInt(Integer.MAX_VALUE) - 1000000000);
            }
            validateHeapWithList(list, heap);
        }
    }

    @Test
    public void InvertedTopTest() {
        try {
            final ArrayKHeap<Integer> heap = ArrayKHeap.newArrayKHeap(cmp, 0);
            assertFalse(heap.invertedTop().isPresent());
        } catch (InvalidInitialSize | InvalidComparator e) {
            e.printStackTrace();
        }
        for(int testNumber = 0; testNumber < 10000; ++testNumber) {
            final int size = rand.nextInt(1000) + 1;
            try {
                final ArrayKHeap<Integer> heap = ArrayKHeap.newArrayKHeap(cmp, size);
                if(size == 0) {
                    assertFalse(heap.invertedTop().isPresent());
                    continue;
                }
                int min = rand.nextInt(Integer.MAX_VALUE);
                heap.add(min);
                for(int i = 1; i < size; ++i) {
                    final int n = rand.nextInt(Integer.MAX_VALUE);
                    heap.add(n);
                    min = Math.min(n, min);
                }
                assertEquals((Integer)min, heap.invertedTop().get());
            } catch (InvalidInitialSize | InvalidComparator e) {
                e.printStackTrace();
            }
        }
    }
}
