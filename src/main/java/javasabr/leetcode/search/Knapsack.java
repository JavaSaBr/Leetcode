package javasabr.leetcode.search;

import java.util.Arrays;

public class Knapsack {

  public static void main(String[] args) {
    int[] profit = new int[] {60, 100, 120 };
    int[] weight = new int[] {10, 20, 30 };
    int maxWeight = 50;
    int length = profit.length;
    System.out.println(recursion(maxWeight, weight, profit, length));
    System.out.println(memo(maxWeight, weight, profit));
    System.out.println(dp2d(maxWeight, weight, profit));
    System.out.println(dp1d(maxWeight, weight, profit));
  }

  public static int recursion(int maxWeight, int[] weights, int[] values, int length) {

    if (length == 0 || maxWeight == 0) {
      return 0;
    }

    int index = length - 1;
    int weight = weights[index];

    if (weight > maxWeight) {
      return recursion(maxWeight, weights, values, length - 1);
    }

    int withOnlyExcluded = recursion(maxWeight, weights, values, length - 1);
    int withSubtractedAndExcluded = recursion(maxWeight - weight, weights, values, length - 1);
    int profit = values[index];

    return Math.max(withOnlyExcluded, profit + withSubtractedAndExcluded);
  }

  public static int memo(int maxWeight, int[] weights, int[] values) {

    int length = weights.length;
    int[][] memo = new int[length][maxWeight + 1];

    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }

    return memo(maxWeight, weights, values, length - 1, memo);
  }

  private static int memo(int maxWeight, int[] weights, int[] values, int index, int[][] memo) {

    if (index < 0) {
      return 0;
    }

    if (memo[index][maxWeight] != -1) {
      return memo[index][maxWeight];
    }

    int weight = weights[index];

    if (weight > maxWeight) {
      memo[index][maxWeight] = memo(maxWeight, weights, values, index - 1, memo);
    } else {
      int profit = values[index];
      int withExcluded = memo(maxWeight, weights, values, index - 1, memo);
      int withSubtractedAndExcluded = memo(maxWeight - weight, weights, values, index - 1, memo);
      memo[index][maxWeight] = Math.max(profit + withSubtractedAndExcluded, withExcluded);
    }

    return memo[index][maxWeight];
  }

  public static int dp2d(int maxWeight, int[] weights, int[] values) {

    int length = weights.length;
    int[][] indexes = new int[length + 1][maxWeight + 1];

    for (int usedItems = 1; usedItems <= length; usedItems++) {

      int itemIndex = usedItems - 1;
      int weight = weights[itemIndex];
      int profit = values[itemIndex];

      for (int targetWeight = 1; targetWeight <= maxWeight; targetWeight++) {
        if (weight <= targetWeight) {
          int diff = targetWeight - weight;
          int withIncluded = profit + indexes[itemIndex][diff];
          int withExcluded = indexes[usedItems - 1][targetWeight];
          indexes[usedItems][targetWeight] = Math.max(withIncluded, withExcluded);
        } else {
          int withExcluded = indexes[usedItems - 1][targetWeight];
          indexes[usedItems][targetWeight] = withExcluded;
        }
      }
    }

    return indexes[length][maxWeight];
  }

  public static int dp1d(int maxWeight, int[] weights, int[] values) {

    int[] indexes = new int[maxWeight + 1];

    for (int usedItems = 1; usedItems < values.length + 1; usedItems++) {
      int itemIndex = usedItems - 1;
      int weight = weights[itemIndex];
      int profit = values[itemIndex];
      for (int targetWeight = maxWeight; targetWeight >= 0; targetWeight--) {
        if (weight <= targetWeight) {
          int withSubtracted = indexes[targetWeight - weight] + profit;
          int currentProfit = indexes[targetWeight];
          indexes[targetWeight] = Math.max(currentProfit, withSubtracted);
        }
      }
    }

    return indexes[maxWeight];
  }
}
