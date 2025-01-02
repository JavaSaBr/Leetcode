package javasabr.leetcode.search;

import java.util.Arrays;

public class SubsetSum {

  public static void main(String[] args) {
    int[] input = {3, 34, 4, 12, 5, 2};

    int sum = 9;
    System.out.println(recursion(input, sum));
    System.out.println(topDown(input, sum));
    System.out.println(bottomUp(input, sum));
    System.out.println(bottomUpSpaceOptimized(input, sum));

    int sum2 = 30;
    System.out.println(recursion(input, sum2));
    System.out.println(topDown(input, sum2));
    System.out.println(bottomUp(input, sum2));
    System.out.println(bottomUpSpaceOptimized(input, sum2));
  }

  public static boolean recursion(int[] array, int sum) {
    return recursion(array, array.length, sum);
  }

  private static boolean recursion(int[] array, int length, int sum) {

    if (sum == 0) {
      return true;
    } else if (length == 0) {
      return false;
    }

    int value = array[length - 1];
    if (value > sum) {
      boolean withExcluded = recursion(array, length - 1, sum);
      return withExcluded;
    }

    boolean withExcluded = recursion(array, length - 1, sum);
    if (withExcluded) {
      return true;
    }

    boolean withReducedSum = recursion(array, length - 1, sum - value);
    return withReducedSum;
  }

  public static boolean topDown(int[] array, int sum) {

    int length = array.length;

    int[][] memo = new int[length + 1][sum + 1];
    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }

    return topDown(array, length, sum, memo);
  }

  private static boolean topDown(int[] array, int length, int sum, int[][] memo) {

    if (sum == 0) {
      return true;
    } else if (length <= 0) {
      return false;
    }

    if (memo[length][sum] != -1) {
      return memo[length][sum] == 1;
    }

    int value = array[length - 1];

    if (value > sum) {
      boolean withExcluded = topDown(array, length - 1, sum, memo);
      memo[length][sum] = withExcluded ? 1 : 0;
      return withExcluded;
    }

    boolean withExcluded = topDown(array, length - 1, sum, memo);
    if (withExcluded) {
      memo[length][sum] = 1;
      return true;
    }

    boolean withReducedSum = topDown(array, length - 1, sum - value, memo);
    memo[length][sum] = withReducedSum ? 1 : 0;

    return withReducedSum;
  }

  public static boolean bottomUp(int[] array, int sum) {

    int length = array.length;
    boolean[][] indexes = new boolean[length + 1][sum + 1];

    for (int i = 0; i <= length; i++) {
      indexes[i][0] = true;
    }

    for (int i = 1; i <= length; i++) {
      int value = array[i - 1];
      for (int j = 1; j <= sum; j++) {
        boolean withExcluded = indexes[i - 1][j];
        if (j < value) {
          indexes[i][j] = withExcluded;
        } else {
          boolean withIncluded = indexes[i - 1][j - value];
          indexes[i][j] = withExcluded || withIncluded;
        }
      }
    }

    return indexes[length][sum];
  }

  // Returns true if there is a subset of arr[]
  // with sum equal to given sum
  static boolean bottomUpSpaceOptimized(int[] array, int sum) {
    int n = array.length;
    boolean[] prev = new boolean[sum + 1];
    boolean[] curr = new boolean[sum + 1];

    // Mark prev[0] = true as it is true to
    // make sum = 0 using 0 elements
    prev[0] = true;

    // Fill the subset table in bottom-up
    // manner
    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= sum; j++) {
        if (j < array[i - 1]) {
          curr[j] = prev[j];
        } else {
          curr[j] = prev[j] || prev[j - array[i - 1]];
        }
      }

      // Update prev to be the current row
      System.arraycopy(curr, 0, prev, 0, sum + 1);
    }
    return prev[sum];
  }
}
