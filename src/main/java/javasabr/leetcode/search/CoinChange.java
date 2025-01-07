package javasabr.leetcode.search;

import java.util.Arrays;

public class CoinChange {

  public static void main(String[] args) {
    int[] coins = {1, 2, 3};
    int sum = 5;
    System.out.println(recursion(coins, sum));
    System.out.println(memo(coins, sum));
    System.out.println(dp2d(coins, sum));
    System.out.println(dp1d(coins, sum));
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

  public static int memo(int[] coins, int sum) {
    int[][] memo = new int[coins.length][sum + 1];
    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }
    return memo(coins, coins.length, sum, memo);
  }

  private static int memo(int[] coins, int length, int sum, int[][] memo) {

    if (sum == 0) {
      return 1;
    } else if (sum < 0 || length == 0) {
      return 0;
    }

    if (memo[length - 1][sum] != -1) {
      return memo[length - 1][sum];
    }

    int coin = coins[length - 1];
    int withSubtractedCoin = memo(coins, length, sum - coin, memo);
    int withExcludedCoin = memo(coins, length - 1, sum, memo);

    return memo[length - 1][sum] = withSubtractedCoin + withExcludedCoin;
  }

  public static int dp2d(int[] coins, int sum) {

    int length = coins.length;

    int[][] indexes = new int[length + 1][sum + 1];
    indexes[0][0] = 1;

    for (int usedCoins = 1; usedCoins <= length; usedCoins++) {
      int coinIndex = usedCoins - 1;
      int coin = coins[coinIndex];
      for (int targetSum = 0; targetSum <= sum; targetSum++) {
        // with excluding this coin
        indexes[usedCoins][targetSum] += indexes[usedCoins - 1][targetSum];
        if ((targetSum - coin) >= 0) {
          // with included this coin but subtracted the sum
          indexes[usedCoins][targetSum] += indexes[usedCoins][targetSum - coin];
        }
      }
    }
    return indexes[length][sum];
  }

  public static int dp1d(int[] coins, int sum) {

    int[] indexes = new int[sum + 1];
    indexes[0] = 1;

    for (int coin : coins) {
      for (int targetSum = coin; targetSum <= sum; targetSum++) {
        indexes[targetSum] += indexes[targetSum - coin];
      }
    }

    return indexes[sum];
  }
}
