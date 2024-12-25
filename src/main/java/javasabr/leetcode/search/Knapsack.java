package javasabr.leetcode.search;

import java.util.Arrays;

public class Knapsack {

  public static void main(String[] args) {
    int[] profit = new int[] {60, 100, 120 };
    int[] weight = new int[] {10, 20, 30 };
    int maxWeight = 50;
    int length = profit.length;
    System.out.println(knapSackRecur(maxWeight, weight, profit, length));
    System.out.println(knapSackMemo(maxWeight, weight, profit));
    System.out.println(bottomUp(maxWeight, weight, profit));
  }

  public static int knapSackRecur(int maxWeight, int[] weights, int[] values, int length) {

    if (length == 0 || maxWeight == 0) {
      return 0;
    } else if (weights[length - 1] > maxWeight) {
      return knapSackRecur(maxWeight, weights, values, length - 1);
    }

    int withIncluded = knapSackRecur(maxWeight, weights, values, length - 1);
    int withExcluded = knapSackRecur(maxWeight - weights[length - 1], weights, values, length - 1);
    return Math.max(withIncluded, values[length - 1] + withExcluded);
  }

  public static int knapSackMemo(int maxWeight, int[] weights, int[] values) {

    int length = weights.length;
    int[][] memo = new int[length][maxWeight + 1];

    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }

    return knapSackMemo(maxWeight, weights, values, length - 1, memo);
  }

  private static int knapSackMemo(int maxWeight, int[] weights, int[] values, int index, int[][] memo) {

    if (index < 0) {
      return 0;
    }

    int[] currentMemo = memo[index];

    if (currentMemo[maxWeight] != -1) {
      return currentMemo[maxWeight];
    }

    int weight = weights[index];

    if (weight > maxWeight) {
      currentMemo[maxWeight] = knapSackMemo(maxWeight, weights, values, index - 1, memo);
    } else {
      int value = values[index];
      int withIncluded = knapSackMemo(maxWeight, weights, values, index - 1, memo);
      int withExcluded = knapSackMemo(maxWeight - weight, weights, values, index - 1, memo);
      currentMemo[maxWeight] = Math.max(value + withExcluded, withIncluded);
    }

    return currentMemo[maxWeight];
  }

  public static int bottomUp(int maxWeight, int[] weights, int[] values) {

    int length = weights.length;
    int[][] indexes = new int[length + 1][maxWeight + 1];

    for (int index = 0; index <= length; index++) {
      for (int targetWeight = 0; targetWeight <= maxWeight; targetWeight++) {
        if (index == 0 || targetWeight == 0) {
          indexes[index][targetWeight] = 0;
          continue;
        }

        int prevIndex = index - 1;
        int weight = weights[prevIndex];

        if (weight <= targetWeight) {
          int weightDiff = targetWeight - weight;
          int withIncluded = values[prevIndex] + indexes[prevIndex][weightDiff];
          int withExcluded = indexes[prevIndex][targetWeight];
          indexes[index][targetWeight] = Math.max(withIncluded, withExcluded);
        } else {
          int withExclude = indexes[prevIndex][targetWeight];
          indexes[index][targetWeight] = withExclude;
        }
      }
    }
    return indexes[length][maxWeight];
  }
}
