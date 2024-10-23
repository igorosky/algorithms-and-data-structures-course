package OneWayArrayList;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Optional;

import aisd.list.IList;

public class OneWayArrayList<T> implements IList<T> {
    private final int m;
    private int len;

    class Node {
        private Node next;
        private T[] value;
        private int elementsCount;

        @SuppressWarnings("unchecked")
        private Node() {
            value = (T[])(new Object[m]);
            elementsCount = 0;
            next = null;
        }

        private void ensureNextExists() {
            if(next == null) {
                next = new Node();
                last = next;
            }
        }

        private void checkIfNextShouldExist() {
            if(next != null && next.elementsCount == 0) {
                next = null;
                last = this;
            }
        }

        private void add(final int idx, final T val) {
            if(idx > elementsCount || idx == m) {
                ensureNextExists();
                next.add(idx - elementsCount, val);
            }
            else {
                if(elementsCount == m) {
                    ensureNextExists();
                    next.add(0, value[m - 1]);
                }
                else {
                    ++elementsCount;
                }
                for(int i = m - 2; i >= idx; --i) {
                    value[i + 1] = value[i];
                }
                value[idx] = val;
            }
        }

        private T remove(final int idx) {
            T ans;
            if(idx >= elementsCount) {
                ans = next.remove(idx - elementsCount);
            }
            else {
                ans = value[idx];
                for(int i = idx; i < elementsCount - 1; ++i) {
                    value[i] = value[i + 1];
                }
                value[--elementsCount] = null;
                --len;
            }
            rebalance();
            return ans;
        }

        private boolean remove(final T val) {
            final boolean ans = _remove(val);
            rebalanceAll();
            return ans;
        }

        private boolean _remove(final T val) {
            boolean removed = false;
            int p = 0;
            for(int i = 0; i < elementsCount; ++i) {
                if(value[i].equals(val)) {
                    removed = true;
                    --len;
                    continue;
                }
                value[p++] = value[i];
            }
            final int oldElementCount = elementsCount;
            elementsCount = p;
            while(p < oldElementCount) {
                value[p++] = null;
            }
            removed |= next != null && next._remove(val);
            return removed;
        }
        
        private void rebalance() {
            if(next != null && elementsCount < m / 2) {
                final T[] newElements = next.steal(m - elementsCount);
                for(int i = 0; i < newElements.length; ++i) {
                    if(newElements[i] == null) {
                        break;
                    }
                    value[elementsCount++] = newElements[i];
                }
            }
            checkIfNextShouldExist();
        }

        private void rebalanceAll() {
            rebalance();
            if(next != null) {
                next.rebalanceAll();
            }
        }

        @SuppressWarnings("unchecked")
        private T[] steal(final int n) {
            final T[] ans = (T[])(new Object[n]);
            final int takenElements = Math.min(elementsCount, n);
            for(int i = 0; i < takenElements; ++i) {
                ans[i] = value[i];
                value[i] = null;
            }
            if(takenElements < n && next != null) {
                final T[] missingElements = next.steal(n - takenElements);
                for(int i = 0; i < missingElements.length; ++i) {
                    ans[takenElements + i] = missingElements[i];
                }
                checkIfNextShouldExist();
            }
            elementsCount -= takenElements;
            rebalance();
            return ans;
        }

        private T get(final int idx) {
            if(idx >= elementsCount) {
                return next.get(idx - elementsCount);
            }
            return value[idx];
        }

        private T set(final int idx, final T val) {
            if(idx >= elementsCount) {
                return next.set(idx - elementsCount, val);
            }
            final T ans =  value[idx];
            value[idx] = val;
            return ans;
        }

        private int indexOf(final T val) {
            for(int i = 0; i < elementsCount; ++i) {
                if(val.equals(value[i])) {
                    return i;
                }
            }
            int ans = -1;
            if(next != null) {
                ans = next.indexOf(val);
            }
            return m * (ans == -1 ? 0 : 1) + ans;
        }

        private int getNodeCount() {
            return 1 + (next == null ? 0 : next.getNodeCount());
        }
    }

    private Node first;
    private Node last;

    private void checkIfInBound(final int idx, final boolean withNext) throws IndexOutOfBoundsException {
        if(idx < 0 || idx > len || !withNext && idx == len) {
            throw new IndexOutOfBoundsException(idx);
        }
    }

    private void createNewIfEmpty() {
        if(last == null) {
            first = new Node();
            last = first;
        }
    }
    
    private OneWayArrayList(final int m) {
        this.m = m;
        len = 0;
        first = null;
        last = null;
    }

    public static <T> Optional<OneWayArrayList<T>> New(final int m) {
        if(m <= 0) {
            return Optional.empty();
        }
        return Optional.of(new OneWayArrayList<T>(m));
    }

    @Override
    public boolean add(final T value) {
        createNewIfEmpty();
        last.add(last.elementsCount, value);
        ++len;
        return true;
    }

    @Override
    public boolean add(final int index, final T value) {
        checkIfInBound(index, true);
        createNewIfEmpty();
        first.add(index, value);
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
    public boolean contains(final T value) {
        return !isEmpty() || first.indexOf(value) != -1;
    }

    @Override
    public T get(final int index) {
        checkIfInBound(index, false);
        return first.get(index);
    }

    @Override
    public T set(final int index, final T value) {
        checkIfInBound(index, false);
        return first.set(index, value);
    }

    @Override
    public int indexOf(final T value) {
        if(isEmpty()) {
            return -1;
        }
        return first.indexOf(value);
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
        final T ans = first.remove(index);
        if(len == 0) {
            clear();
        }
        return ans;
    }

    @Override
    public boolean remove(final T value) {
        if(isEmpty()) {
            return false;
        }
        final boolean ans = first.remove(value);
        if(len == 0) {
            clear();
        }
        return ans;
    }

    @Override
    public int size() {
        return len;
    }
    
    public int getNodeCount() {
        return isEmpty() ? 0 : first.getNodeCount();
    }

    @SuppressWarnings("unchecked")
    public Object[] toArray() {
        final T[] ans = (T[])(new Object[size()]);
        Node iter = first;
        int p = 0;
        while(iter != null) {
            for(int i = 0; i < iter.elementsCount; ++i) {
                ans[p++] = iter.get(i);
            }
            iter = iter.next;
        }
        return ans;
    }
}
