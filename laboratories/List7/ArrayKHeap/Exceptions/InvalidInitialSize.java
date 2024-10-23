package ArrayKHeap.Exceptions;

public class InvalidInitialSize extends Exception {
    public InvalidInitialSize() {
        super("Initial size cannot be 0 or less");
    }
}
