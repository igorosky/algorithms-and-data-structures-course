package TwoWayLinkedList;

import java.util.Iterator;
import java.util.ListIterator;

import aisd.list.IList;

public class TwoWayLinkedList<T> implements IList<T> {

    int len;

    class Node {
        private Node next;
        private Node prev;
        private T val;

        private Node(final T val) {
            this.val = val;
            next = null;
            prev = null;
        }
    }

    private Node first;
    private Node last;

    private void checkIfInBound(final int idx, final boolean withNext) throws IndexOutOfBoundsException {
        if(idx < 0 || idx > len || !withNext && idx == len) {
            throw new IndexOutOfBoundsException(idx);
        }
    }

    public TwoWayLinkedList() {
        first = null;
        last = null;
        len = 0;
    }
    
    @Override
    public boolean add(T value) {
        if(isEmpty()) {
            first = new Node(value);
            last = first;
        }
        else {
            last.next = new Node(value);
            last.next.prev = last;
            last = last.next;
        }
        ++len;
        return true;
    }

    @Override
    public boolean add(int index, T value) {
        checkIfInBound(index, true);
        if(isEmpty()) {
            first = new Node(value);
            last = first;
        }
        else if(index == 0) {
            first.prev = new Node(value);
            first.prev.next = first;
            first = first.prev;
        }
        else if(index == len) {
            last.next = new Node(value);
            last.next.prev = last;
            last = last.next;
        }
        else {
            Node iter = getNode(index - 1);
            final Node newNode = new Node(value);
            newNode.prev = iter;
            newNode.next = iter.next;
            if(iter.next != null) {
                iter.next.prev = newNode;
            }
            iter.next = newNode;
        }
        ++len;
        return true;
    }

    @Override
    public void clear() {
        last = null;
        first = null;
        len = 0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    private Node getNode(int index) {
        checkIfInBound(index, false);
        Node iter = first;
        for(int i = 0; i < index; ++i) {
            iter = iter.next;
        }
        return iter;
    }

    @Override
    public T get(int index) {
        return getNode(index).val;
    }

    @Override
    public T set(int index, T value) {
        Node node = getNode(index);
        final T ans = node.val;
        node.val = value;
        return ans;
    }

    @Override
    public int indexOf(T value) {
        Node iter = first;
        for(int i = 0; i < len; ++i) {
            if(iter.val.equals(value)) {
                return i;
            }
            iter = iter.next;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return len == 0;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'iterator'");
    }

    @Override
    public ListIterator<T> listIterator() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
    }

    @Override
    public T remove(final int index) {
        checkIfInBound(index, false);
        final T ans;
        if(len == 1) {
            ans = first.val;
            clear();
        }
        else if(index == 0) {
            ans = first.val;
            first = first.next;
            first.prev = null;
            --len;
        }
        else if(index == len - 1) {
            ans = last.val;
            last = last.prev;
            --len;
        }
        else {
            Node iter = first;
            for(int i = 1; i < index; ++i) {
                iter = iter.next;
            }
            ans = iter.next.val;
            iter.next = iter.next.next;
            iter.next.next.prev = iter;
            --len;
        }
        return ans;
    }

    @Override
    public boolean remove(T value) {
        Node iter = first;
        boolean ans = false;
        while(iter != null && iter.val.equals(value)) {
            iter = iter.next;
            --len;
            ans = true;
        }
        if(iter == null) {
            clear();
            return ans;
        }
        iter.prev = null;
        first = iter;
        while(iter.next != null) {
            if(iter.next.val.equals(value)) {
                if(iter.next.next != null) {
                    iter.next.next.prev = iter;
                }
                iter.next = iter.next.next;
                --len;
            }
            else {
                iter = iter.next;
            }
        }
        last = iter;
        return ans;
    }

    @Override
    public int size() {
        return len;
    }

    public TwoWayLinkedList<T> interleave(final TwoWayLinkedList<T> other) {
        final TwoWayLinkedList<T> ans = new TwoWayLinkedList<>();
        if(!isEmpty()) {
            ans.first = first;
            ans.last = first;
            first = first.next;
            --len;
            ++ans.len;
            if(isEmpty()) {
                clear();
            }
            else {
                first.prev = null;
            }
            ans.first.next = null;
            interleaveHelper(ans, other, this);
        }
        return ans;
    }

    private static <T> void interleaveHelper(final TwoWayLinkedList<T> ans, final TwoWayLinkedList<T> first, final TwoWayLinkedList<T> second) {
        if(first.isEmpty()) {
            return;
        }
        --first.len;
        ++ans.len;
        ans.last.next = first.first;
        first.first.prev = ans.last;
        if(first.isEmpty()) {
            first.clear();
        }
        else {
            first.first = first.first.next;
            first.first.prev = null;
        }
        ans.last = ans.last.next;
        ans.last.next = null;
        interleaveHelper(ans, second, first);
    }
}
