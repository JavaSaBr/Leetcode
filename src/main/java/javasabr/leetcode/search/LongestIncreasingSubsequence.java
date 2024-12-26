package javasabr.leetcode.search;

import java.util.Arrays;

public class LongestIncreasingSubsequence {

  public static void main(String[] args) {
    int[] array = { 10, 22, 9, 33, 21, 50, 41, 60 };
    System.out.println(recursion(array));
    System.out.println(memoization(array));
    System.out.println(bottomUp(array));
  }

  public static int recursion(int[] array) {

    int length = array.length;
    int longest = 1;

    for (int limit = 1; limit < length; limit++) {
      int result = recursion(array, limit);
      longest = Math.max(longest, result);
    }

    return longest;
  }

  private static int recursion(int[] array, int limit) {

    if (limit == 0) {
      return 1;
    }

    int longest = 1;

    for (int i = 0; i < limit; i++) {
      if (array[i] < array[limit]) {
        int result = recursion(array, i);
        longest = Math.max(longest, result + 1);
      }
    }

    return longest;
  }

  public static int memoization(int[] arr) {

    int length = arr.length;

    int[] storedResults = new int[length];
    Arrays.fill(storedResults, -1);

    int longest = 1;
    for (int limit = 1; limit < length; limit++) {
      int result = memoization(arr, limit, storedResults);
      longest = Math.max(longest, result);
    }

    return longest;
  }

  private static int memoization(int[] array, int limit, int[] storedResults) {

    if (limit == 0) {
      return 1;
    } else if (storedResults[limit] != -1) {
      return storedResults[limit];
    }

    int longest = 1;
    for (int i = 0; i < limit; i++) {
      if (array[i] < array[limit]) {
        int result = memoization(array, i, storedResults);
        longest = Math.max(longest, result + 1);
      }
    }

    storedResults[limit] = longest;

    return storedResults[limit];
  }

  public static int bottomUp(int[] array) {

    int length = array.length;
    int[] indexes = new int[length];

    Arrays.fill(indexes, 1);

    int max = 1;

    for (int limit = 1; limit < length; limit++) {
      int value = array[limit];
      for (int i = 0; i < limit; i++) {
        if (array[i] > value) {
          continue;
        }
        indexes[limit] = Math.max(indexes[limit], indexes[i] + 1);
        max = Math.max(max, indexes[limit]);
      }
    }

    return max;
  }
}
