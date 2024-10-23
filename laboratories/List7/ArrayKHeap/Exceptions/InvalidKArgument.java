package ArrayKHeap.Exceptions;

public class InvalidKArgument extends Exception {
    public InvalidKArgument() {
        super("K argument cannot be 0 or less");
    }
}
