public class Ex3 {
    public static void reverse(int[] nums, int p, int q) {
        for(int i = 0; i < (q - p) / 2; ++i) {
            final int tmp = nums[p + i];
            nums[p + i] = nums[q - i - 1];
            nums[q - i - 1] = tmp;
        }
    }
    
    public static void nextPermutation(int[] nums) {
        int q = nums.length - 1;
        while(q > 0 && nums[q - 1] >= nums[q]) {
            --q;
        }
        if(q == 0) {
            reverse(nums, 0, nums.length);
            return;
        }
        final int p = q - 1;
        final int tmp = nums[p];
        while(q + 1 < nums.length && nums[q + 1] > tmp) {
            ++q;
        }
        nums[p] = nums[q];
        nums[q] = tmp;
        reverse(nums, p + 1, nums.length);
    }
}
