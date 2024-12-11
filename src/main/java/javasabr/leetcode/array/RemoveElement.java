package javasabr.leetcode.array;

public class RemoveElement {

    public static void main(String[] args) {

    }

    public static int removeElement(int[] nums, int val) {
        int removed = 0;

        for (int i = 0, j = 0; i < nums.length; i++) {
            int currentValue = nums[i];

            if (currentValue == val) {
                removed++;
                continue;
            }

            nums[j++] = currentValue;
        }

        return nums.length - removed;
    }
}
