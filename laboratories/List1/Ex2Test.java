import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

public class Ex2Test {
    @Test
    public void randomTests() {
        final Random rand = new Random(System.currentTimeMillis());
        for(int testNumber = 0; testNumber < 10000; ++testNumber) {
            final int[] testCase = new int[rand.nextInt(100000)];
            for(int j = 0; j < testCase.length; ++j) {
                testCase[j] = rand.nextInt(2000000001) - 1000000000;
            }

            final int[][] testCaseSolution = Ex2.longestNondecreasingSubstrings(testCase);

            int p = 0;
            for(int i = 0; i < testCaseSolution.length; ++i) {
                int prev = Integer.MIN_VALUE;
                for(int j = 0; j < testCaseSolution[i].length; ++j) {
                    assertTrue(testCaseSolution[i][j] >= prev);
                    assertTrue(p < testCase.length);
                    assertEquals(testCase[p++], testCaseSolution[i][j]);
                    prev = Math.max(prev, testCaseSolution[i][j]);
                }
            }
            assertEquals(p, testCase.length);
        }
    }

    @Test
    public void randomTestsMod() {
        final Random rand = new Random(System.currentTimeMillis());
        for(int testNumber = 0; testNumber < 10000; ++testNumber) {
            final int[] testCase = new int[rand.nextInt(100000)];
            for(int j = 0; j < testCase.length; ++j) {
                testCase[j] = rand.nextInt(2000000001) - 1000000000;
            }

            final Ex2.ArrString[] testCaseSolution = Ex2.longestNondecreasingSubstringsMod(testCase);

            int p = 0;
            for(final Ex2.ArrString subStr : testCaseSolution) {
                if(subStr.getIndex() > 0) {
                    assertTrue(testCase[subStr.getIndex() - 1] > testCase[subStr.getIndex()]);
                }
                for(int i = subStr.getIndex() + 1; i < subStr.getLen(); ++i) {
                    assertTrue(testCase[i - 1] <= testCase[i]);
                }
                p += subStr.getLen();
            }
            assertEquals(p, testCase.length);
        }
    }
}
