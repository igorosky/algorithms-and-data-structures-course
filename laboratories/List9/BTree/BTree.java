package BTree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class BTree<T> {
    private int maxNodeSize;
    private Comparator<? super T> comparator;
    private Node root;
    private int treeSize;
    
    private class Node {
        private final T[] values;
        private final ArrayList<Node> children;
        private Node parent;
        private int len;
        private boolean isLeaf;
        private int childNumber;

        private int lowerBound(final T value) {
            int p = 0;
            int q = len;
            while(p + 1 < q) {
                final int s = (p + q) / 2;
                if(comparator.compare(values[s], value) > 0) {
                    q = s;
                }
                else {
                    p = s;
                }
            }
            if(values[p] != null && comparator.compare(values[p], value) < 0) {
                ++p;
            }
            return p;
        }
        
        // true when overflown
        private boolean insert(final int idx, final T value) {
            for(int i = len; i > idx; --i) {
                values[i] = values[i - 1];
            }
            values[idx] = value;
            return ++len > maxNodeSize;
        }

        private boolean erase(final int idx) {
            for(int i = idx; i + 1 < len; ++i) {
                values[i] = values[i + 1];
            }
            values[--len] = null;
            return len < maxNodeSize / 2;
        }

        private boolean insert(final int idx, final T value, final Node child) {
            final boolean ans = insert(idx, value);
            for(int i = len; i > idx + 1; --i) {
                setChild(i, children.get(i - 1));
            }
            setChild(idx + 1, child);
            return ans;
        }

        private void setChild(final int index, final Node value) {
            if(value != null) {
                value.childNumber = index;
                value.parent = this;
            }
            children.set(index, value);
        }

        private Node createRightSibling() {
            final int half = maxNodeSize / 2;
            final Node sibling = new Node();
            sibling.isLeaf = isLeaf;
            for(int i = half + 1; i < values.length; ++i) {
                sibling.values[sibling.len++] = values[i];
                values[i] = null;
            }
            if(!isLeaf) {
                for(int i = half + 1; i <= values.length; ++i) {
                    sibling.setChild(i - half - 1, children.get(i));
                    setChild(i, null);
                }
            }
            len = half + 1;
            return sibling;
        }

        private T getMax() {
            return isLeaf ? values[len - 1] : children.get(len).getMax();
        }

        private T getMin() {
            return isLeaf ? values[0] : children.get(0).getMin();
        }

        private void fixOverflow() {
            if(parent == null) {
                parent = new Node(this);
                root = parent;
            }
            else {
                parent.insertFromChild(this);
            }
        }

        private void fillChildrenGap(int idx, final int last) {
            for(; idx < last; ++idx) {
                setChild(idx, children.get(idx + 1));
            }
            setChild(last, null);
        }

        private void fixChildUnderflow(int childIdx) {
            Node child = children.get(childIdx);
            if(childIdx > 0 && children.get(childIdx - 1).len > maxNodeSize / 2) {
                child.insert(0, values[childIdx - 1]);
                final Node previousChild = children.get(childIdx - 1);
                values[childIdx - 1] = previousChild.values[previousChild.len - 1];
                previousChild.erase(previousChild.len - 1);
                if(!child.isLeaf) {
                    for(int i = child.len; i > 0; --i) {
                        child.setChild(i, child.children.get(i - 1));
                    }
                    child.setChild(0, previousChild.children.get(previousChild.len + 1));
                    previousChild.fillChildrenGap(previousChild.len + 1, previousChild.len + 1);
                }
                
                child.add(values[childIdx - 1]);
                values[childIdx - 1] = children.get(childIdx - 1).getMax();
                children.get(childIdx - 1).remove(values[childIdx - 1]);
            }
            else if(childIdx + 1 <= len && children.get(childIdx + 1).len > maxNodeSize / 2) {
                child.values[child.len++] = values[childIdx];
                final Node nextChild = children.get(childIdx + 1);
                values[childIdx] = nextChild.values[0];
                nextChild.erase(0);
                if(!child.isLeaf) {
                    child.setChild(child.len, nextChild.children.get(0));
                    nextChild.fillChildrenGap(0, nextChild.len + 1);
                }
            }
            else {
                if(childIdx == len) {
                    child = children.get(--childIdx);
                }
                child.values[child.len++] = values[childIdx];
                erase(childIdx);
                final Node nextChild = children.get(childIdx + 1);
                int childPreviousLength = child.len;
                for(int i = 0; i < nextChild.len; ++i) {
                    child.values[child.len++] = nextChild.values[i];
                }
                fillChildrenGap(childIdx + 1, len + 1);
                if(!child.isLeaf) {
                    for(int i = 0; i <= nextChild.len; ++i) {
                        child.setChild(childPreviousLength++, nextChild.children.get(i));
                    }
                }
                if(len == 0 && parent == null) {
                    root = children.get(childIdx);
                    root.parent = null;
                }
                else if(len < maxNodeSize / 2 && parent != null) {
                    parent.fixChildUnderflow(childNumber);
                }
            }
        }
        
        @SuppressWarnings("unchecked")
        private Node() {
            values = (T[])(new Object[maxNodeSize + 1]);
            children = new ArrayList<>(maxNodeSize + 2);
            for(int i = 0; i < maxNodeSize + 2; ++i) {
                children.add(null);
            }
            this.parent = null;
            len = 0;
            isLeaf = true;
        }

        private Node(final Node child) {
            this();
            isLeaf = false;
            setChild(0, child);
            insertFromChild(child);
        }

        private void insertFromChild(final Node child) {
            final int half = maxNodeSize / 2;

            final boolean overflown = insert(lowerBound(child.values[half]), child.values[half], child.createRightSibling());
            child.values[--child.len] = null;

            if(overflown) {
                fixOverflow();
            }
        }

        private Optional<T> find(final T value) {
            final int idx = lowerBound(value);
            if(values[idx] != null && comparator.compare(value, values[idx]) == 0) {
                return Optional.of(values[idx]);
            }
            return isLeaf ? Optional.empty() : children.get(idx).find(value);
        }

        private boolean add(final T value) {
            final int idx = lowerBound(value);
            if(values[idx] != null && comparator.compare(values[idx], value) == 0) {
                return false;
            }
            if(isLeaf) {
                if(insert(idx, value)) {
                    fixOverflow();
                }
                return true;
            }
            return children.get(idx).add(value);
        }

        private Optional<T> remove(final T value) {
            final int idx = lowerBound(value);
            if(values[idx] != null && comparator.compare(values[idx], value) == 0) {
                final T ans = values[idx];
                if(isLeaf) {
                    if(erase(idx) && parent != null) {
                        parent.fixChildUnderflow(childNumber);
                    }
                }
                else {
                    final T replacement = children.get(idx).getMax();
                    values[idx] = replacement;
                    children.get(idx).remove(replacement);
                }
                return Optional.of(ans);
            }
            return isLeaf ? Optional.empty() : children.get(idx).remove(value);
        }

        private void print() {
            System.out.print("Values: ");
            for(int i = 0; i < len; ++i) {
                System.out.printf("%d, ", values[i]);
            }
            System.out.println();
            if(parent != null) {
                System.out.printf("First parent value: %d\nChild number: %d\n", parent.values[0], childNumber);
            }
            else {
                System.out.println("I am root");
            }
            if(!isLeaf) {
                System.out.println("Children: {");
                for(int i = 0; i <= len; ++i) {
                    System.out.printf("Child %d:\n", i);
                    children.get(i).print();
                }
                System.out.println("}");
            }
        }
    }

    public BTree(final int maxNodeSize, Comparator<? super T> comparator) {
        this.comparator = comparator;
        this.maxNodeSize = maxNodeSize;
        root = new Node();
        treeSize = 0;
    }

    public Optional<T> find(final T value) {
        return root.find(value);
    }

    public boolean add(final T value) {
        final boolean ans = root.add(value);
        if(ans) {
            ++treeSize;
        }
        return ans;
    }

    public Optional<T> remove(final T value) {
        final Optional<T> ans = root.remove(value);
        if(ans.isPresent()) {
            --treeSize;
        }
        return ans;
    }

    public void clear() {
        root = new Node();
        treeSize = 0;
    }

    public Optional<T> getMax() {
        return root.len != 0 ? Optional.of(root.getMax()) : Optional.empty() ;
    }

    public Optional<T> getMin() {
        return root.len != 0 ? Optional.of(root.getMin()) : Optional.empty() ;
    }

    public int size() {
        return treeSize;
    }

    public void print() {
        root.print();
        System.out.println("EOT");
    }
}
