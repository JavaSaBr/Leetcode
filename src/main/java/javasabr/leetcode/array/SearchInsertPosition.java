package javasabr.leetcode.array;

public class SearchInsertPosition {

    public static void main(String[] args) {

    }

    public static int searchInsert(int[] nums, int target) {
        int high = nums.length - 1;
        int low = 0;
        int closestIndex = 0;

        while (low <= high) {

            int mid = low + (high - low) / 2;
            int midValue = nums[mid];

            if (midValue == target) {
                return mid;
            } else if (midValue < target) {
                low = mid + 1;
                closestIndex = mid;
            } else {
                high = mid - 1;
            }
        }

        for (int i = closestIndex; i < nums.length; i++) {
            if (nums[i] > target) {
                return i;
            }
        }

        return nums.length;
    }
}
