package javasabr.leetcode.repeat;

import java.util.Arrays;

public class LongestIncreasingSubsequence {

  public static void main(String[] args) {
    int[] array = { 10, 22, 9, 33, 21, 50, 41, 60 };
    System.out.println(recursion(array));
    System.out.println(dp1d(array));
  }

  public static int recursion(int[] array) {

    int longest = 1;

    for (int limit = 1; limit < array.length; limit++) {
      int countUntilLimit = recursion(array, limit);
      longest = Math.max(longest, countUntilLimit);
    }

    return longest;
  }

  public static int recursion(int[] array, int length) {

    int longest = 1;
    int highestValue = array[length];

    for (int limit = 0; limit < length; limit++) {
      if (array[limit] < highestValue) {
        int countUntilLimit = recursion(array, limit);
        longest = Math.max(longest, countUntilLimit + 1);
      }
    }

    return longest;
  }

  public static int dp1d(int[] array) {

    int[] indexes = new int[array.length + 1];
    Arrays.fill(indexes, 1);

    int longest = 1;

    for (int limit = 1; limit < array.length; limit++) {
      int highestValue = array[limit];
      for (int i = 0; i < limit; i++) {
        if (array[i] < highestValue) {
          indexes[limit] = Math.max(indexes[limit], indexes[i] + 1);
          longest = Math.max(longest, indexes[limit]);
        }
      }
    }

    return longest;
  }
}
