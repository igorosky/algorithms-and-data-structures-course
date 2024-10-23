public class Ex1 {
    final private static int period = 8;
    final private static char space = ' ';
    final private static char c = 'X';
    final private static char patternC = 'O';

    final public static void printCharNTimes(final char c, final int n) {
        for(int i = 0; i < n; ++i) {
            System.out.print(c);
        }
    }
    
    static public void drawZigZag(final int n, final int l) {
        final int halfPeriod = period / 2;
        for(int row = 0; row < n; ++row) {
            int spaceWidth = row % period;
            if(spaceWidth > halfPeriod) {
                spaceWidth = period - spaceWidth;
            }
            printCharNTimes(space, spaceWidth);
            printCharNTimes(c, l);
            System.out.println();
        }
    }

    static public void drawScarf(final int n, final int k) {
        final int halfPeriod = period / 2;
        final int q = k - 1;
        for(int row = 0; row < n; ++row) {
            int spaceWidth = row % period;
            if(spaceWidth > halfPeriod) {
                spaceWidth = period - spaceWidth;
            }
            for(int i = 0; i < spaceWidth; ++i) {
                System.out.print(space);
            }
            int p = row % (2 * q);
            if(p > q) {
                p = q * 2 - p;
            }
            printCharNTimes(c, k - p);
            printCharNTimes(patternC, 1 + p * 2);
            printCharNTimes(c, k - p);
            System.out.println();
        }
    }
}
