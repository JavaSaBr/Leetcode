package javasabr.leetcode.search;

import java.util.Arrays;

public class CoinChange {

  public static void main(String[] args) {
    int[] coins = {1, 2, 3};
    int sum = 5;
    System.out.println(recursion(coins, sum));
    System.out.println(recursionWithMemo(coins, sum));
    System.out.println(bottomUp(coins, sum));
    System.out.println(bottomUpSpaceOptimized(coins, sum));
  }

  public static int recursion(int[] coins, int sum) {
    return recursion(coins, coins.length, sum);
  }

  private static int recursion(int[] coins, int length, int sum) {

    if (sum == 0) {
      return 1;
    } else if (sum < 0 || length == 0) {
      return 0;
    }

    int coin = coins[length - 1];
    int withSubtractedCoin = recursion(coins, length, sum - coin);
    int withExcludedCoin = recursion(coins, length - 1, sum);

    return withSubtractedCoin + withExcludedCoin;
  }

  public static int recursionWithMemo(int[] coins, int sum) {
    int[][] memo = new int[coins.length][sum + 1];
    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }
    return recursionWithMemo(coins, coins.length, sum, memo);
  }

  private static int recursionWithMemo(int[] coins, int length, int sum, int[][] memo) {

    if (sum == 0) {
      return 1;
    } else if (sum < 0 || length == 0) {
      return 0;
    }

    if (memo[length - 1][sum] != -1) {
      return memo[length - 1][sum];
    }

    int coin = coins[length - 1];
    int withSubtractedCoin = recursionWithMemo(coins, length, sum - coin, memo);
    int withExcludedCoin = recursionWithMemo(coins, length - 1, sum, memo);

    return memo[length - 1][sum] = withSubtractedCoin + withExcludedCoin;
  }

  public static int bottomUp(int[] coins, int sum) {

    int length = coins.length;

    int[][] indexes = new int[length + 1][sum + 1];
    indexes[0][0] = 1;

    for (int index = 1; index <= length; index++) {
      int prev = index - 1;
      int coin = coins[prev];
      for (int targetSum = 0; targetSum <= sum; targetSum++) {
        indexes[index][targetSum] += indexes[prev][targetSum];
        if ((targetSum - coin) >= 0) {
          indexes[index][targetSum] += indexes[index][targetSum - coin];
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
