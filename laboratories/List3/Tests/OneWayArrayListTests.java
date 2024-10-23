package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

import OneWayArrayList.OneWayArrayList;

public class OneWayArrayListTests {
    @Test
    public void creationTest() {
        assertTrue(OneWayArrayList.<Integer>New(2).isPresent());
        assertTrue(OneWayArrayList.<Integer>New(0).isEmpty());
        assertTrue(OneWayArrayList.<Integer>New(-1).isEmpty());
        final var listOpt = OneWayArrayList.<Integer>New(100);
        assertTrue(listOpt.isPresent());
        final var list = listOpt.get();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(0, list.getNodeCount());
    }
    
    @Test
    public void basicTest() {
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(0, list.getNodeCount());
        
        list.add(2);
        assertEquals(1, list.size());
        assertEquals(1, list.getNodeCount());
        assertFalse(list.isEmpty());
        
        list.add(10);
        assertEquals(2, list.size());
        assertEquals(1, list.getNodeCount());
        assertFalse(list.isEmpty());

        list.add(15);
        assertEquals(3, list.size());
        assertEquals(2, list.getNodeCount());
        assertFalse(list.isEmpty());
        
        assertEquals(2, (int)list.get(0));
        assertEquals(10, (int)list.get(1));
        assertEquals(15, (int)list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(0, list.getNodeCount());
    }

    @Test
    public void addingElementOnIndex() {
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        
        list.add(0, 2);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertEquals(1, list.getNodeCount());
        
        list.add(1, 15);
        assertEquals(1, list.getNodeCount());
        assertEquals(2, list.size());
        assertFalse(list.isEmpty());

        list.add(1, 10);
        assertEquals(2, list.getNodeCount());
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());

        assertEquals(2, (int)list.get(0));
        assertEquals(10, (int)list.get(1));
        assertEquals(15, (int)list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, 2));
    }

    @Test
    public void removingElement() {
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        
        list.add(2);
        list.add(3);
        list.add(4);
        assertEquals(2, list.getNodeCount());
        assertEquals(4, (int)list.remove(2));
        assertEquals(1, list.getNodeCount());
        list.add(5);

        assertEquals(2, (int)list.get(0));
        assertEquals(3, (int)list.get(1));
        assertEquals(5, (int)list.get(2));
        
        assertEquals(2, list.getNodeCount());
        assertEquals(5, (int)list.remove(2));
        assertEquals(1, list.getNodeCount());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

        list.add(10);
        assertEquals(2, (int)list.remove(0));
        assertEquals(2, list.getNodeCount());
        assertEquals(2, list.size());
        assertEquals(3, (int)list.get(0));
        assertEquals(10, (int)list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
        assertEquals(3, (int)list.remove(0));
        assertEquals(1, list.size());
        assertEquals(1, list.getNodeCount());
        
        assertEquals(10, (int)list.remove(0));
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(0, list.getNodeCount());
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    public void removingElementWithValue() {
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(2);
        assertEquals(5, list.size());
        assertEquals(3, list.getNodeCount());

        assertFalse(list.remove((Integer)10));
        assertEquals(5, list.size());
        assertEquals(3, list.getNodeCount());
        assertTrue(list.remove((Integer)2));
        assertFalse(list.remove((Integer)2));
        assertEquals(1, list.size());
        assertEquals(1, list.getNodeCount());
        assertEquals(3, (int)list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));

        assertTrue(list.remove((Integer)3));
        assertFalse(list.remove((Integer)3));
        
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertEquals(0, list.getNodeCount());
    }

    @Test
    public void indexOfContains() {
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        assertEquals(-1, list.indexOf(5));
        list.add(2);
        list.add(4);
        list.add(3);
        assertEquals(0, list.indexOf(2));
        assertTrue(list.contains(2));
        list.add(2);
        assertEquals(0, list.indexOf(2));
        assertTrue(list.contains(2));
        assertEquals(-1, list.indexOf(5));
        list.add(5);
        assertEquals(4, list.indexOf(5));
        assertTrue(list.contains(5));
        assertEquals(1, list.indexOf(4));
        assertTrue(list.contains(4));
        list.add(0, 5);
        assertEquals(0, list.indexOf(5));
        assertTrue(list.contains(5));
    }

    @Test
    public void set() {
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, 2));
        list.add(10);
        list.add(25);
        list.add(30);
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, 2));
        assertEquals(25, (int)list.set(1, 20));
        assertEquals(10, (int)list.get(0));
        assertEquals(20, (int)list.get(1));
        assertEquals(30, (int)list.get(2));
    }

    @Test
    public void toArray() {
        final Random rand = new Random(System.currentTimeMillis());
        final int[] original = new int[rand.nextInt(1000000)];
        final OneWayArrayList<Integer> list = OneWayArrayList.<Integer>New(2).get();
        for(int i = 0; i < original.length; ++i) {
            final int number = rand.nextInt(Integer.MAX_VALUE);
            original[i] = number;
            list.add(number);
        }
        
        Object[] arr = list.toArray();
        assertEquals(original.length, arr.length);
        for(int i = 0; i < original.length; ++i) {
            assertEquals(original[i], (int)arr[i]);
        }
    }
}
