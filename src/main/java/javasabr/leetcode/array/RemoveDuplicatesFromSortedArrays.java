package javasabr.leetcode.array;

public class RemoveDuplicatesFromSortedArrays {

    public static void main(String[] args) {

    }

    public static int removeDuplicates(int[] nums) {
        int uniqCount = 0;
        int prev = Integer.MIN_VALUE;

        for (int i = 0, j = 0; i < nums.length; i++) {
            int current = nums[i];
            if (current != prev) {
                nums[j++] = current;
                prev = current;
                uniqCount++;
            }
        }

        return uniqCount;
    }
}
