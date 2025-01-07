package javasabr.leetcode.repeat;

import java.util.Arrays;

public class CoinChange {

  public static void main(String[] args) {
    int[] coins1 = {1, 2, 3};
    int[] coins2 = {4};
    int sum = 5;
    System.out.println(recursion(coins1, coins1.length, sum));
    System.out.println(recursion(coins2, coins2.length, sum));
    System.out.println(memo(coins1, sum));
    System.out.println(memo(coins2, sum));
    System.out.println(dp2d(coins1, sum));
    System.out.println(dp2d(coins2, sum));
    System.out.println(dp1d(coins1, sum));
    System.out.println(dp1d(coins2, sum));
  }

  public static int recursion(int[] coins, int length, int sum) {

    if (length < 1) {
      return 0;
    } else if (sum == 0) {
      return 1;
    }

    int index = length - 1;
    int coin = coins[index];

    if (coin > sum) {
      return recursion(coins, index, sum);
    }

    int withReducedSum = recursion(coins, length, sum - coin);
    int withExcludedCoin = recursion(coins, index, sum);

    return withReducedSum + withExcludedCoin;
  }

  public static int memo(int[] coins, int sum) {
    int[][] memo = new int[coins.length][sum + 1];

    for (int[] array : memo) {
      Arrays.fill(array, -1);
    }

    return recursion(coins, coins.length, sum, memo);
  }

  public static int recursion(int[] coins, int length, int sum, int[][] memo) {

    if (length < 1) {
      return 0;
    } else if (sum == 0) {
      return 1;
    }

    int index = length - 1;

    if (memo[index][sum] != -1) {
      return memo[index][sum];
    }

    int coin = coins[index];

    if (coin > sum) {
      return recursion(coins, index, sum, memo);
    }

    int withReducedSum = recursion(coins, length, sum - coin, memo);
    int withExcludedCoin = recursion(coins, index, sum, memo);

    memo[index][sum] = withReducedSum + withExcludedCoin;
    return memo[index][sum];
  }

  public static int dp2d(int[] coins, int sum) {

    int[][] indexes = new int[coins.length + 1][sum + 1];
    indexes[0][0] = 1;

    for (int usedCoins = 1; usedCoins <= coins.length; usedCoins++) {
      int coinIndex = usedCoins - 1;
      int coin = coins[coinIndex];
      for (int targetSum = 0; targetSum <= sum; targetSum++) {
        // with excluding this coin
        indexes[usedCoins][targetSum] += indexes[usedCoins - 1][targetSum];
        if (targetSum - coin >= 0) {
          // with included this coin but subtracted the sum
          indexes[usedCoins][targetSum] += indexes[usedCoins][targetSum - coin];
        }
      }
    }

    return indexes[coins.length][sum];
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
