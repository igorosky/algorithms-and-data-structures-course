package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import TwoWayLinkedList.TwoWayLinkedList;

public class TwoWayLinkedListTests {
    @Test
    public void basicTest() {
        final TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        
        list.add(2);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        
        list.add(10);
        assertEquals(2, list.size());
        assertFalse(list.isEmpty());

        list.add(15);
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
        
        assertEquals(2, (int)list.get(0));
        assertEquals(10, (int)list.get(1));
        assertEquals(15, (int)list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3));

        list.clear();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void addingElementOnIndex() {
        final TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
        
        list.add(0, 2);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        
        list.add(1, 15);
        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
        assertEquals(15, (int)list.get(1));

        list.add(1, 10);
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
        final TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
        
        list.add(2);
        list.add(3);
        list.add(4);
        assertEquals(4, (int)list.remove(2));
        list.add(5);

        assertEquals(2, (int)list.get(0));
        assertEquals(3, (int)list.get(1));
        assertEquals(5, (int)list.get(2));
        
        assertEquals(5, (int)list.remove(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

        list.add(10);
        assertEquals(2, (int)list.remove(0));
        assertEquals(2, list.size());
        assertEquals(3, (int)list.get(0));
        assertEquals(10, (int)list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
        assertEquals(3, (int)list.remove(0));
        assertEquals(1, list.size());
        
        assertEquals(10, (int)list.remove(0));
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    }

    @Test
    public void removingElementWithValue() {
        final TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(2);
        assertEquals(5, list.size());

        assertFalse(list.remove((Integer)10));
        assertEquals(5, list.size());
        assertTrue(list.remove((Integer)2));
        assertFalse(list.remove((Integer)2));
        assertEquals(1, list.size());
        assertEquals(3, (int)list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));

        assertTrue(list.remove((Integer)3));
        assertFalse(list.remove((Integer)3));
        
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void indexOfContains() {
        final TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
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
        final TwoWayLinkedList<Integer> list = new TwoWayLinkedList<Integer>();
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
    public void InterleaveTest() {
        final TwoWayLinkedList<Integer> list1 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list2 = new TwoWayLinkedList<Integer>();

        list1.add(1);
        list1.add(3);
        list1.add(5);

        list2.add(2);
        list2.add(4);
        list2.add(6);

        final TwoWayLinkedList<Integer> list3 = list1.interleave(list2);

        assertTrue(list1.isEmpty());
        assertTrue(list2.isEmpty());
        assertFalse(list3.isEmpty());
        assertEquals(6, list3.size());
        for(int i = 0; i < 6; ++i) {
            assertEquals(i + 1, (int)list3.get(i));
        }
    }
    
    @Test
    public void InterleaveMoreInFirstTest() {
        final TwoWayLinkedList<Integer> list1 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list2 = new TwoWayLinkedList<Integer>();

        list1.add(1);
        list1.add(3);
        list1.add(5);
        list1.add(7);

        list2.add(2);
        list2.add(4);
        list2.add(6);

        final TwoWayLinkedList<Integer> list3 = list1.interleave(list2);

        assertTrue(list1.isEmpty());
        assertTrue(list2.isEmpty());
        assertFalse(list3.isEmpty());
        assertEquals(7, list3.size());
        for(int i = 0; i < 7; ++i) {
            assertEquals(i + 1, (int)list3.get(i));
        }
    }
    
    @Test
    public void InterleaveMoreInSecondTest() {
        final TwoWayLinkedList<Integer> list1 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list2 = new TwoWayLinkedList<Integer>();

        list1.add(1);
        list1.add(3);
        list1.add(5);
        
        list2.add(2);
        list2.add(4);
        list2.add(6);
        list2.add(7);
        
        final TwoWayLinkedList<Integer> list3 = list1.interleave(list2);
        
        assertTrue(list1.isEmpty());
        assertFalse(list2.isEmpty());
        assertEquals(1, list2.size());
        assertFalse(list3.isEmpty());
        assertEquals(6, list3.size());
        for(int i = 0; i < 6; ++i) {
            assertEquals(i + 1, (int)list3.get(i));
        }
        assertEquals(7, (int)list2.get(0));
    }
    
    @Test
    public void InterleaveFirstEmptyTest() {
        final TwoWayLinkedList<Integer> list1 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list2 = new TwoWayLinkedList<Integer>();

        list2.add(2);
        
        final TwoWayLinkedList<Integer> list3 = list1.interleave(list2);
        assertTrue(list1.isEmpty());
        assertFalse(list2.isEmpty());
        assertEquals(1, list2.size());
        assertTrue(list3.isEmpty());
        assertEquals(2, (int)list2.get(0));
    }
    
    @Test
    public void InterleaveSecondEmptyTest() {
        final TwoWayLinkedList<Integer> list1 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list2 = new TwoWayLinkedList<Integer>();

        list1.add(2);
        
        final TwoWayLinkedList<Integer> list3 = list1.interleave(list2);
        assertTrue(list1.isEmpty());
        assertTrue(list2.isEmpty());
        assertFalse(list3.isEmpty());
        assertEquals(1, list3.size());
        assertEquals(2, (int)list3.get(0));
    }

    @Test
    public void InterleaveTwoEmptyTest() {
        final TwoWayLinkedList<Integer> list1 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list2 = new TwoWayLinkedList<Integer>();
        final TwoWayLinkedList<Integer> list3 = list1.interleave(list2);
        assertTrue(list1.isEmpty());
        assertTrue(list2.isEmpty());
        assertTrue(list3.isEmpty());
    }
}
