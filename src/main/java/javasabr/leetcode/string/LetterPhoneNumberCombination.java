package javasabr.leetcode.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LetterPhoneNumberCombination {

  private static final int CHAR_OFFSET = 48;
  private static final char[][] LETTER_MATRIX_1 = new char[10][];
  private static final char[][][][] LETTER_MATRIX_2 = new char[10][10][][];

  static {
    LETTER_MATRIX_1[2] = new char[]{'a', 'b', 'c'};
    LETTER_MATRIX_1[3] = new char[]{'d', 'e', 'f'};
    LETTER_MATRIX_1[4] = new char[]{'g', 'h', 'i'};
    LETTER_MATRIX_1[5] = new char[]{'j', 'k', 'l'};
    LETTER_MATRIX_1[6] = new char[]{'m', 'n', 'o'};
    LETTER_MATRIX_1[7] = new char[]{'p', 'q', 'r', 's'};
    LETTER_MATRIX_1[8] = new char[]{'t', 'u', 'v'};
    LETTER_MATRIX_1[9] = new char[]{'w', 'x', 'y', 'z'};

    for (int i = 2; i < 10; i++) {
      for (int j = 2; j < 10; j++) {
        LETTER_MATRIX_2[i][j] = prepare(i + "" + j);
      }
    }
  }

  public static void main(String[] args) {
    System.out.println(baseline("5"));
    // [j, k, l]
    System.out.println(baseline("22"));
    // [aa, ab, ac, ba, bb, bc, ca, cb, cc]
    System.out.println(baseline("23"));
    System.out.println(optimize1("23"));
    // [ad, ae, af, bd, be, bf, cd, ce, cf]
    // [ad, ae, af, bd, be, bf, cd, ce, cf, da, db, dc, ea, eb, ec, fa, fb, fc]
    System.out.println(baseline("234"));
    System.out.println(optimize1("234"));
    // [adg, aeh, afi, bdg, beh, bfi, cdg, ceh, cfi, dag, dbh, dci, eag, ebh, eci, fag, fbh, fci, gad, gbe, gcf, had, hbe, hcf, iad, ibe, icf]
    // [adg, adh, adi, aeg, aeh, aei, afg, afh, afi, bdg, bdh, bdi, beg, beh, bei, bfg, bfh, bfi, cdg, cdh, cdi, ceg, ceh, cei, cfg, cfh, cfi]
    System.out.println(baseline("2345"));
    System.out.println(optimize1("2345"));
  }

  public static List<String> optimize1(String digits) {

    if (digits.isEmpty()) {
      return List.of();
    }

    var result = new ArrayList<String>();
    int length = digits.length();

    if (length == 1) {
      char[] letters = LETTER_MATRIX_1[digits.charAt(0) - CHAR_OFFSET];
      for (char letter : letters) {
        result.add(String.valueOf(letter));
      }
      return result;
    }

    char[] buffer = new char[length];
    appendBy2(result, digits, buffer, 0);

    return result;
  }

  public static List<String> baseline(String digits) {

    if (digits.isEmpty()) {
      return List.of();
    }

    var result = new ArrayList<String>();
    int length = digits.length();

    if (length == 1) {
      char[] letters = LETTER_MATRIX_1[digits.charAt(0) - CHAR_OFFSET];
      for (char letter : letters) {
        result.add(String.valueOf(letter));
      }
      return result;
    }

    char[] buffer = new char[length];
    appendBy1(result, digits, buffer, 0);

    return result;
  }

  private static char[][] prepare(String digits) {

    var result = new ArrayList<char[]>();
    int length = digits.length();

    char[] buffer = new char[length];
    prepare(result, digits, buffer, 0);
    char[][] chars = new char[result.size()][];

    for (int i = 0; i < chars.length; i++) {
      chars[i] = result.get(i);
    }

    return chars;
  }

  private static void prepare(List<char[]> result, String digits, char[] buffer, int level) {

    if (level == digits.length()) {
      result.add(Arrays.copyOfRange(buffer, 0, level));
      return;
    }

    char digit = digits.charAt(level);
    char[] letters = LETTER_MATRIX_1[digit - CHAR_OFFSET];

    for (char letter : letters) {
      buffer[level] = letter;
      prepare(result, digits, buffer, level + 1);
    }
  }

  private static void appendBy1(List<String> result, String digits, char[] buffer, int level) {

    if (level == digits.length()) {
      result.add(String.valueOf(buffer, 0, level));
      return;
    }

    char digit = digits.charAt(level);
    char[] letters = LETTER_MATRIX_1[digit - CHAR_OFFSET];

    for (char letter : letters) {
      buffer[level] = letter;
      appendBy1(result, digits, buffer, level + 1);
    }
  }

  private static void appendBy2(List<String> result, String digits, char[] buffer, int level) {

    int length = digits.length();
    if (level == length) {
      result.add(String.valueOf(buffer, 0, level));
      return;
    }

    int remaining = length - level;

    if (remaining >= 2) {
      char first = digits.charAt(level);
      char second = digits.charAt(level + 1);
      char[][] variants = LETTER_MATRIX_2[first - CHAR_OFFSET][second - CHAR_OFFSET];
      for (char[] variant : variants) {
        buffer[level] = variant[0];
        buffer[level + 1] = variant[1];
        appendBy2(result, digits, buffer, level + 2);
      }
    } else {
      char digit = digits.charAt(level);
      char[] letters = LETTER_MATRIX_1[digit - CHAR_OFFSET];
      for (char letter : letters) {
        buffer[level] = letter;
        appendBy2(result, digits, buffer, level + 1);
      }
    }
  }
}
