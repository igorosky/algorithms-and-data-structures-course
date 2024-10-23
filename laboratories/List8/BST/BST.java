package BST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BST<T> implements Iterable<T> {
    private class Node {
        private final T value;
        private Node leftChild;
        private Node rightChild;
        private final Comparator<? super T> comparator;

        Node(final Comparator<? super T> comparator, final T value) {
            this.value = value;
            this.comparator = comparator;
            leftChild = null;
            rightChild = null;
        }

        Optional<T> find(final T value) {
            switch (comparator.compare(value, this.value)) {
                case -1:
                    return leftChild != null ? leftChild.find(value) : Optional.empty();
                case 0:
                    return Optional.of(this.value);
                case 1:
                    return rightChild != null ? rightChild.find(value) : Optional.empty();                
            }
            return Optional.empty();
        }

        T minimum() {
            return leftChild != null ? leftChild.minimum() : this.value;
        }

        T maximum() {
            return rightChild != null ? rightChild.maximum() : this.value;
        }

        List<T> lowerThan(final T val) {
            ArrayList<T> ans = new ArrayList<>();
            if(leftChild != null) {
                ans.addAll(leftChild.lowerThan(val));
            }
            if(comparator.compare(value, val) < 0) {
                ans.add(value);
                if(rightChild != null) {
                    ans.addAll(rightChild.lowerThan(val));
                }
            }
            return ans;
        }
    }

    private Optional<Node> root;
    private final Comparator<? super T> comparator;

    public BST(final Comparator<? super T> comparator) {
        root = Optional.empty();
        this.comparator = comparator;
    }

    public Optional<T> find(final T value) {
        return root.isPresent() ? root.get().find(value) : Optional.empty();
    }

    public Optional<T> minimum() {
        return root.isPresent() ? Optional.of(root.get().minimum()) : Optional.empty();
    }

    public Optional<T> maximum() {
        return root.isPresent() ? Optional.of(root.get().maximum()) : Optional.empty();
    }

    public boolean insert(final T value) {
        if(root.isEmpty()) {
            root = Optional.of(new Node(comparator, value));
            return true;
        }
        Node iter = root.get();
        while(true) {
            switch(comparator.compare(value, iter.value)) {
                case -1:
                    if(iter.leftChild == null) {
                        iter.leftChild = new Node(comparator, value);
                        return true;
                    }
                    iter = iter.leftChild;
                    break;
                case 1:
                    if(iter.rightChild == null) {
                        iter.rightChild = new Node(comparator, value);
                        return true;
                    }
                    iter = iter.rightChild;
                    break;
                default:
                    return false;
            }
        }
    }

    public boolean remove(final T value) {
        if(root.isEmpty()) {
            return false;
        }

        Node parent = null;
        Node iter = root.get();
        while(!Objects.equals(iter.value, value)) {
            parent = iter;
            switch(comparator.compare(value, iter.value)) {
                case -1:
                    if(iter.leftChild == null) {
                        return false;
                    }
                    iter = iter.leftChild;
                    break;
                case 1:
                    if(iter.rightChild == null) {
                        return false;
                    }
                    iter = iter.rightChild;
                    break;
            }
        }
        if(iter.leftChild == null && iter.rightChild == null) {
            if(parent == null) {
                root = Optional.empty();
            }
            else switch(comparator.compare(parent.value, iter.value)) {
                case -1:
                    parent.rightChild = null;
                    break;
                case 1:
                    parent.leftChild = null;
                    break;
            }
        }
        else if(iter.leftChild != null && iter.rightChild != null) {
            final Node left = iter.leftChild;
            if(parent == null) {
                root = Optional.of(iter.rightChild);
            }
            else switch(comparator.compare(parent.value, iter.value)) {
                case -1:
                    parent.rightChild = iter.rightChild;
                    break;
                case 1:
                    parent.leftChild = iter.rightChild;
                    break;
            }
            iter = iter.rightChild;
            while(iter.leftChild != null) {
                iter = iter.leftChild;
            }
            iter.leftChild = left;
        }
        else if(iter.leftChild != null) {
            if(parent == null) {
                root = Optional.of(iter.leftChild);
            }
            else switch(comparator.compare(parent.value, iter.value)) {
                case -1:
                    parent.rightChild = iter.leftChild;
                    break;
                case 1:
                    parent.leftChild = iter.leftChild;
                    break;
            }
        }
        else /*if(iter.rightChild != null)*/ {
            if(parent == null) {
                root = Optional.of(iter.rightChild);
            }
            else switch(comparator.compare(parent.value, iter.value)) {
                case -1:
                    parent.rightChild = iter.rightChild;
                    break;
                case 1:
                    parent.leftChild = iter.rightChild;
                    break;
            }
        }
        return true;
    }

    public Optional<T> findNext(final T value) {
        if(root.isEmpty()) {
            return Optional.empty();
        }
        Optional<T> prev = Optional.empty();
        Node iter = root.get();
        while(true) {
            switch(comparator.compare(value, iter.value)) {
                case -1:
                    if(prev.isEmpty() || comparator.compare(prev.get(), iter.value) < 0) {
                        prev = Optional.of(iter.value);
                    }
                    if(iter.leftChild == null) {
                        return Optional.of(iter.value);
                    }
                    iter = iter.leftChild;
                    break;
                default:
                    if(iter.rightChild == null) {
                        return prev;
                    }
                    iter = iter.rightChild;
                    break;
            }
        }
    }

    public boolean clear() {
        final boolean ans = root.isPresent();
        root = Optional.empty();
        return ans;
    }

    // public List<T> lowerThan(final T val) {
    //     ArrayList<T> ans = new ArrayList<>();
    //     Optional<T> currentOpt = minimum();
    //     if(currentOpt.isEmpty()) {
    //         return ans;
    //     }
    //     T current = currentOpt.get();
    //     while(comparator.compare(current, val) < 0) {
    //         ans.add(current);
    //         currentOpt = findNext(current);
    //         if(currentOpt.isEmpty()) {
    //             break;
    //         }
    //         current = currentOpt.get();
    //     }
    //     return ans;
    // }

    public List<T> lowerThan(final T val) {
        if(root.isEmpty()) {
            return new ArrayList<>();
        }
        return root.get().lowerThan(val);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter(root);
    }

    public class Iter implements Iterator<T> {

        private int done;
        private Node node;
        private Iter iter;
        
        private Iter(final Optional<Node> node) {
            if(node.isEmpty()) {
                done = 3;
            }
            else {
                this.node = node.get();
            }
        }

        @Override
        public boolean hasNext() {
            return done < 3;
        }

        @Override
        public T next() {
            switch(done) {
                case 0:
                    if(node.leftChild != null) {
                        done = 1;
                        iter = new Iter(Optional.of(node.leftChild));
                    }
                    else if(node.rightChild != null) {
                        done = 2;
                        iter = new Iter(Optional.of(node.rightChild));
                    }
                    else {
                        done = 3;
                    }
                    return node.value;
                case 1:
                    {
                        final T ans = iter.next();
                        if(!iter.hasNext()) {
                            if(node.rightChild != null) {
                                done = 2;
                                iter = new Iter(Optional.of(node.rightChild));
                            }
                            else {
                                done = 3;
                            }
                        }
                        return ans;
                    }
                default:
                    final T ans = iter.next();
                    if(!iter.hasNext()) {
                        done = 3;
                    }
                    return ans;
            }
        }
    }
}
