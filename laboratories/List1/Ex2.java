import java.util.ArrayList;

public class Ex2 {
    static class ArrString {
        final private int index;
        private int len;

        private ArrString(final int index, final int len) {
            this.index = index;
            this.len = len;
        }

        public int getIndex() {
            return index;
        }

        public int getLen() {
            return len;
        }
    }
    
    public static int[][] longestNondecreasingSubstrings(final int[] numbers) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        if(numbers.length != 0) {
            ans.add(new ArrayList<>());
            ans.get(0).add(numbers[0]);
            for(int i = 1; i < numbers.length; ++i) {
                if(ans.get(ans.size() - 1).get(ans.get(ans.size() - 1).size() - 1) > numbers[i]) {
                    ans.add(new ArrayList<>());
                }
                ans.get(ans.size() - 1).add(numbers[i]);
            }
        }
        
        int[][] trueAns = new int[ans.size()][];
        for(int i = 0; i < ans.size(); ++i) {
            trueAns[i] = new int[ans.get(i).size()];
            for(int j = 0; j < ans.get(i).size(); ++j) {
                trueAns[i][j] = ans.get(i).get(j);
            }
        }
        return trueAns;
    }
    
    public static ArrString[] longestNondecreasingSubstringsMod(final int[] numbers) {
        ArrayList<ArrString> ans = new ArrayList<>();

        if(numbers.length != 0) {
            ans.add(new ArrString(0, 1));
            for(int i = 1; i < numbers.length; ++i) {
                if(numbers[i - 1] > numbers[i]) {
                    ans.add(new ArrString(i, 0));
                }
                ++ans.get(ans.size() - 1).len;
            }
        }
        
        ArrString[] trueAns = new ArrString[ans.size()];
        for(int i = 0; i < ans.size(); ++i) {
            trueAns[i] = ans.get(i);
        }
        return trueAns;
    }
}
