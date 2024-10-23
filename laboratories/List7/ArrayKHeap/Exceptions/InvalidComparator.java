package ArrayKHeap.Exceptions;

public class InvalidComparator extends Exception {
    public InvalidComparator() {
        super("Comparator cannot be null");
    }
}
