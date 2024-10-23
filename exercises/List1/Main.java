public class Main {
    public static void printlnArr(int[] arr) {
        for(int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i]);
            System.out.print(' ');
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        final int[] ex1 = {1,2,1};
        printlnArr(Ex1.NextPascalLine(ex1));
        try {
            final int[] ex2 = {1,2,3};
            System.out.println(Ex2.getSecondSmallest(ex2));
        }
        catch(NoAnswerException e) {
            System.err.println("Ex2");
        }
        try {
            final int[] ex2 = {1,1,1};
            System.out.println(Ex2.getSecondSmallest(ex2));
            System.err.println("Ex2.2");
        }
        catch(NoAnswerException e) {
            System.out.println("Git");
        }
        final int[] ex3 = {1,2,3};
        Ex3.nextPermutation(ex3);
        printlnArr(ex3);
    }
}
