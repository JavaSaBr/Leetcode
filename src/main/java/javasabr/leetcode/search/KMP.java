package javasabr.leetcode.search;

import java.util.ArrayList;
import java.util.List;

public class KMP {

  public static void main(String[] args) {

    String txt = "aabaacaadaabaaba";
    String pat = "aaba";

    var indexes = search(pat, txt);

    for (int index : indexes)
      System.out.print(index + " ");
  }

  public static List<Integer> search(String pattern, String text) {

    int textLength = text.length();
    int patternLength = pattern.length();

    int[] lps = constructLps(pattern);

    var result = new ArrayList<Integer>();

    int i = 0;
    int j = 0;

    while (i < textLength) {

      char left = text.charAt(i);
      char right = pattern.charAt(j);

      if (left == right) {
        i++;
        j++;
        if (j == patternLength) {
          result.add(i - j);
          j = lps[j - 1];
        }
      } else {
        if (j != 0) {
          j = lps[j - 1];
        } else {
          i++;
        }
      }
    }

    return result;
  }

  private static int[] constructLps(String pattern) {

    int patternLength = pattern.length();
    int[] lps = new int[patternLength];

    int prefixLength = 0;
    // lps[0] is always 0
    int i = 1;

    while (i < patternLength) {

      char left = pattern.charAt(i);
      char right = pattern.charAt(prefixLength);

      if (left == right) {
        prefixLength++;
        lps[i] = prefixLength;
        i++;
      } else {
        if (prefixLength != 0) {
          prefixLength = lps[prefixLength - 1];
        } else {
          lps[i] = 0;
          i++;
        }
      }
    }

    return lps;
  }
}
