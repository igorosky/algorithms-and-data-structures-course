public class Ex1 {
    static public int[] NextPascalLine(final int[] nums) {
        int[] ans = new int[nums.length + 1];
        ans[0] = 1;
        ans[nums.length] = 1;
        for(int i = 1; i < nums.length; ++i) {
            ans[i] = nums[i - 1] + nums[i];
        }
        return ans;
    }
}
