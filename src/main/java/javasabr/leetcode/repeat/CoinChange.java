package javasabr.leetcode.repeat;

public class CoinChange {

  public static void main(String[] args) {
    int[] coins1 = {1, 2, 3};
    int[] coins2 = {4};
    int sum = 5;
    System.out.println(bottomUp(coins1, sum));
    System.out.println(bottomUp(coins2, sum));
    System.out.println(recursion(coins1, coins1.length, sum));
    System.out.println(recursion(coins2, coins2.length, sum));
  }

  public static int recursion(int[] coins, int length, int sum) {

    if (sum == 0) {
      return 1;
    } else if (sum < 0 || length == 0) {
      return 0;
    }

    int coin = coins[length - 1];
    int withReducedSum = recursion(coins, length, sum - coin);
    int withExcluded = recursion(coins, length  -1, sum);

    return withReducedSum + withExcluded;
  }

  public static int bottomUp(int[] coins, int sum) {

    int length = coins.length;
    int[][] indexes = new int[length + 1][sum + 1];
    indexes[0][0] = 1;

    for(int limit = 1; limit <= length; limit++) {
      int index = limit - 1;
      int coin = coins[index];
      for (int targetSum = 0; targetSum <= sum; targetSum++) {
        indexes[limit][targetSum] += indexes[index][targetSum];
        if (targetSum - coin >= 0) {
          indexes[limit][targetSum] += indexes[index][targetSum - coin];
        }
      }
    }

    return indexes[length][sum];
  }
}
