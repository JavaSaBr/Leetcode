package javasabr.leetcode.search;

import java.util.Arrays;

public class LongestIncreasingSubsequence {

  public static void main(String[] args) {
    int[] array = { 10, 22, 9, 33, 21, 50, 41, 60 };
    System.out.println(recursion(array));
    System.out.println(memo(array));
    System.out.println(dp1d(array));
  }

  public static int recursion(int[] array) {

    int length = array.length;
    int longest = 1;

    for (int limit = 1; limit < length; limit++) {
      int countUntilLimit = recursion(array, limit);
      longest = Math.max(longest, countUntilLimit);
    }

    return longest;
  }

  private static int recursion(int[] array, int length) {

    if (length == 0) {
      return 1;
    }

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

  public static int memo(int[] array) {

    int length = array.length;

    int[] indexes = new int[length];
    Arrays.fill(indexes, -1);

    int longest = 1;
    for (int limit = 1; limit < length; limit++) {
      int countUntilLimit = memo(array, limit, indexes);
      longest = Math.max(longest, countUntilLimit);
    }

    return longest;
  }

  private static int memo(int[] array, int length, int[] indexes) {

    if (length == 0) {
      return 1;
    } else if (indexes[length] != -1) {
      return indexes[length];
    }

    int longest = 1;
    int highestValue = array[length];

    for (int limit = 0; limit < length; limit++) {
      if (array[limit] < highestValue) {
        int countUntilLimit = memo(array, limit, indexes);
        longest = Math.max(longest, countUntilLimit + 1);
      }
    }

    indexes[length] = longest;

    return longest;
  }

  public static int dp1d(int[] array) {

    int length = array.length;
    int[] indexes = new int[length];

    Arrays.fill(indexes, 1);

    int max = 1;

    for (int limit = 1; limit < length; limit++) {
      int highestValue = array[limit];
      for (int i = 0; i < limit; i++) {
        if (array[i] > highestValue) {
          continue;
        }
        indexes[limit] = Math.max(indexes[limit], indexes[i] + 1);
        max = Math.max(max, indexes[limit]);
      }
    }

    return max;
  }
}
