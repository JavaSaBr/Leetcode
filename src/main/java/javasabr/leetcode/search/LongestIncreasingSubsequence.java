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

    for (int idx = 1; idx < length; idx++) {
      int result = recursion(array, idx);
      longest = Math.max(longest, result);
    }

    return longest;
  }

  private static int recursion(int[] array, int idx) {

    if (idx == 0) {
      return 1;
    }

    int longest = 1;

    for (int prev = 0; prev < idx; prev++) {
      if (array[prev] < array[idx]) {
        int result = recursion(array, prev);
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
    for (int idx = 1; idx < length; idx++) {
      int result = memoization(arr, idx, storedResults);
      longest = Math.max(longest, result);
    }

    return longest;
  }

  private static int memoization(int[] array, int idx, int[] storedResults) {

    if (idx == 0) {
      return 1;
    } else if (storedResults[idx] != -1) {
      return storedResults[idx];
    }

    int longest = 1;
    for (int prev = 0; prev < idx; prev++) {
      if (array[prev] < array[idx]) {
        int result = memoization(array, prev, storedResults);
        longest = Math.max(longest, result + 1);
      }
    }

    storedResults[idx] = longest;

    return storedResults[idx];
  }

  public static int bottomUp(int[] array) {

    int length = array.length;
    int[] indexes = new int[length];

    Arrays.fill(indexes, 1);

    int max = 1;

    for (int i = 1; i < length; i++) {
      int value = array[i];
      for (int j = 0; j < i; j++) {
        int prevValue = array[j];
        if (value < prevValue) {
          continue;
        }
        indexes[i] = Math.max(indexes[i], indexes[j] + 1);
        max = Math.max(max, indexes[i]);
      }
    }

    return max;
  }
}
