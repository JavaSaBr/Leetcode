package javasabr.leetcode.repeat;

import java.util.Arrays;

public class Knapsack {

  public static void main(String[] args) {
    int[] profit = new int[] {60, 100, 120 };
    int[] weight = new int[] {10, 20, 30 };
    int maxWeight = 50;
    int length = profit.length;
    System.out.println(recursion(maxWeight, weight, profit, length));
    System.out.println(memo(maxWeight, weight, profit));
    System.out.println(dp1d(maxWeight, weight, profit));
  }

  public static int recursion(int maxWeight, int[] weights, int[] values, int length) {

    if (maxWeight < 0 || length < 1) {
      return 0;
    }

    int index = length - 1;
    int weight = weights[index];

    if (weight > maxWeight) {
      return recursion(maxWeight, weights, values, length - 1);
    }

    int profit = values[index];
    int withExcluded = recursion(maxWeight, weights, values, length - 1);
    int withSubtracted = profit + recursion(maxWeight - weight, weights, values, length - 1);

    return Math.max(withExcluded, withSubtracted);
  }

  public static int memo(int maxWeight, int[] weights, int[] values) {

    int[][] memo = new int[weights.length + 1][maxWeight + 1];

    for (int[] array : memo) {
      Arrays.fill(array, -1);
    }

    return memo(maxWeight, weights, values, values.length, memo);
  }

  private static int memo(int maxWeight, int[] weights, int[] values, int length, int[][] memo) {

    if (maxWeight < 0 || length < 1) {
      return 0;
    }

    int index = length - 1;
    int weight = weights[index];

    if (weight > maxWeight) {
      return memo(maxWeight, weights, values, length - 1, memo);
    }

    if (memo[index][maxWeight] != -1) {
      return memo[index][maxWeight];
    }

    int profit = values[index];
    int withExcluded = memo(maxWeight, weights, values, length - 1, memo);
    int withSubtracted = profit + memo(maxWeight - weight, weights, values, length - 1, memo);

    int result = Math.max(withExcluded, withSubtracted);

    memo[index][maxWeight] = result;

    return result;
  }

  public static int dp1d(int maxWeight, int[] weights, int[] values) {

    int[] indexes = new int[maxWeight + 1];

    for (int usedItems = 1; usedItems <= values.length; usedItems++) {
      int itemIndex = usedItems - 1;
      int weight = weights[itemIndex];
      int profit = values[itemIndex];
      for (int targetWeight = maxWeight; targetWeight >= 0; targetWeight--) {
        if (weight <= targetWeight) {
          int withSubtracted = indexes[targetWeight - weight] + profit;
          indexes[targetWeight] = Math.max(indexes[targetWeight], withSubtracted);
        }
      }
    }

    return indexes[maxWeight];
  }
}
