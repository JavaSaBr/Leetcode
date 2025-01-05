package javasabr.leetcode.search;

public class LongestCommonSubstring {

  public static void main(String[] args) {
    String s1 = "geeksforgeeks";
    String s2 = "practicewritegeekscourses";
    System.out.println(simple(s1, s2));
    System.out.println(recursive(s1, s2));
    System.out.println(dp2d(s1, s2));
    System.out.println(dp1d(s1, s2));
  }

  public static int simple(String leftString, String rightString) {

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    int longest = 0;

    for (int i = 0; i < leftLength; i++) {
      for (int j = 0; j < rightLength; j++) {
        int current = 0;

        while (true) {
          int nextI = i + current;
          int nextJ = j + current;

          if (nextI >= leftLength || nextJ >= rightLength) {
            break;
          }

          char left = leftString.charAt(nextI);
          char right = rightString.charAt(nextJ);

          if (left != right) {
            break;
          }

          current++;
        }

        longest = Math.max(longest, current);
      }
    }
    return longest;
  }

  public static int recursive(String leftString, String rightString) {

    int result = 0;

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    for (int i = 1; i <= leftLength; i++) {
      for (int j = 1; j <= rightLength; j++) {
        result = Math.max(result, recursive(leftString, rightString, i, j));
      }
    }

    return result;
  }

  private static int recursive(String leftString, String rightString, int leftIndex, int rightIndex) {

    if (leftIndex == 0 || rightIndex == 0) {
      return 0;
    } else {
      char left = leftString.charAt(leftIndex - 1);
      char right = rightString.charAt(rightIndex - 1);
      if (left != right) {
        return 0;
      }
    }

    return 1 + recursive(leftString, rightString, leftIndex - 1, rightIndex - 1);
  }

  public static int dp2d(String leftString, String rightString) {

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    int[][] indexes = new int[leftLength + 1][rightLength + 1];

    int result = 0;

    for (int i = 1; i <= leftLength; i++) {
      int checkI = i - 1;
      for (int j = 1; j <= rightLength; j++) {
        int checkJ = j - 1;
        // if it's the same then store prev counter + 1
        if (leftString.charAt(checkI) == rightString.charAt(checkJ)) {
          indexes[i][j] = indexes[checkI][checkJ] + 1;
          result = Math.max(result, indexes[i][j]);
        } else {
          indexes[i][j] = 0;
        }
      }
    }

    return result;
  }

  public static int dp1d(String leftString, String rightString) {

    int leftLength = leftString.length();
    int rightLength = rightString.length();

    // previous row's results
    int[] prevRow = new int[rightLength + 1];

    int result = 0;

    for (int i = 1; i <= leftLength; i++) {

      char left = leftString.charAt(i - 1);
      int[] currentRow = new int[rightLength + 1];

      for (int j = 1; j <= rightLength; j++) {
        char right = rightString.charAt(j - 1);
        if (left == right) {
          currentRow[j] = prevRow[j - 1] + 1;
          result = Math.max(result, currentRow[j]);
        } else {
          currentRow[j] = 0;
        }
      }

      // store the temp row
      prevRow = currentRow;
    }

    return result;
  }
}
