package javasabr.leetcode.search;

import java.util.Arrays;

public class CoinChange {

  public static void main(String[] args) {
    int[] coins = {1, 2, 3};
    int sum = 5;
    System.out.println(countRecur(coins, sum));
    System.out.println(countRecurWithMemo(coins, sum));
    System.out.println(bottomUp(coins, sum));
    System.out.println(bottomUpSpaceOptimized(coins, sum));
  }

  public static int countRecur(int[] coins, int sum) {
    return countRecur(coins, coins.length, sum);
  }

  private static int countRecur(int[] coins, int length, int sum) {

    if (sum == 0) {
      return 1;
    } else if (sum < 0 || length == 0) {
      return 0;
    }

    int lastCoin = coins[length - 1];
    int reducedSumOnLastCoin = countRecur(coins, length, sum - lastCoin);
    int withoutLastCoin = countRecur(coins, length - 1, sum);

    return reducedSumOnLastCoin + withoutLastCoin;
  }

  public static int countRecurWithMemo(int[] coins, int sum) {
    int[][] memo = new int[coins.length][sum + 1];
    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }
    return countRecurWithMemo(coins, coins.length, sum, memo);
  }

  private static int countRecurWithMemo(int[] coins, int length, int sum, int[][] memo) {

    if (sum == 0) {
      return 1;
    } else if (sum < 0 || length == 0) {
      return 0;
    }

    if (memo[length - 1][sum] != -1) {
      return memo[length - 1][sum];
    }

    int lastCoin = coins[length - 1];
    int reducedSumOnLastCoin = countRecurWithMemo(coins, length, sum - lastCoin, memo);
    int withoutLastCoin = countRecurWithMemo(coins, length - 1, sum, memo);

    return memo[length - 1][sum] = reducedSumOnLastCoin + withoutLastCoin;
  }

  public static int bottomUp(int[] coins, int sum) {

    int length = coins.length;

    int[][] indexes = new int[length + 1][sum + 1];
    indexes[0][0] = 1;

    for (int i = 1; i <= length; i++) {
      for (int j = 0; j <= sum; j++) {

        indexes[i][j] += indexes[i - 1][j];

        int prevCoin = coins[i - 1];
        if ((j - prevCoin) >= 0) {
          indexes[i][j] += indexes[i][j - prevCoin];
        }
      }
    }
    return indexes[length][sum];
  }

  public static int bottomUpSpaceOptimized(int[] coins, int sum) {

    int[] indexes = new int[sum + 1];
    indexes[0] = 1;

    for (int coin : coins) {
      for (int j = coin; j <= sum; j++) {
        indexes[j] += indexes[j - coin];
      }
    }

    return indexes[sum];
  }
}
