package javasabr.leetcode.repeat;

public class Knapsack {

  public static void main(String[] args) {
    int[] profit = new int[] {60, 100, 120 };
    int[] weight = new int[] {10, 20, 30 };
    int maxWeight = 50;
    int length = profit.length;
    System.out.println(recursion(maxWeight, weight, profit, length));
    System.out.println(bottomUp(maxWeight, weight, profit));
  }

  public static int recursion(int maxWeight, int[] weights, int[] values, int length) {

    if(length == 0) {
      return 0;
    }

    int weight = weights[length - 1];

    if (weight > maxWeight) {
      return recursion(maxWeight, weights, values, length - 1);
    }

    int value = values[length - 1];

    int withExcluded = recursion(maxWeight, weights, values, length - 1);
    int withReducedMaxWeight = recursion(maxWeight - weight, weights, values, length - 1);

    return Math.max(withExcluded, withReducedMaxWeight + value);
  }

  public static int bottomUp(int maxWeight, int[] weights, int[] values) {

    int length = values.length;
    int[][] indexes = new int[length + 1][maxWeight + 1];

    for(int limit = 1; limit <= length; limit++) {

      int index = limit - 1;
      int weight = weights[index];
      int value = values[index];

      for (int targetWeight = 0; targetWeight <= maxWeight; targetWeight++) {
        if (weight <= targetWeight) {
          int diff = targetWeight - weight;
          int toCopy = indexes[index][targetWeight];
          int withReducedMaxWeight = indexes[index][diff];
          indexes[limit][targetWeight] = Math.max(toCopy, withReducedMaxWeight + value);
        } else {
          indexes[limit][targetWeight] = indexes[index][targetWeight];
        }
      }
    }

    return indexes[length][maxWeight];
  }
}
