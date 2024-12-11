package javasabr.leetcode.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

  public static void main(String[] args) {
    int[] result = hashMapBased(new int[]{2, 7, 11, 15}, 9);
    System.out.println(Arrays.toString(result));
  }

  public static int[] hashMapBased(int[] nums, int target) {

    int length = nums.length;

    Map<Integer, Integer> hashMap = new HashMap<>();

    for (int i = 0; i < length; i++) {

      int left = nums[i];
      int diff = target - left;

      Integer rightIndex = hashMap.get(diff);
      hashMap.putIfAbsent(left, i);

      if (rightIndex != null) {
        return new int[] { i, rightIndex };
      }
    }

    return new int[] { -1, -1 };
  }
}
