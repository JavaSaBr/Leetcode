package javasabr.leetcode.string;

public class ZigzagConversion {

  public static void main(String[] args) {
    //System.out.println(ZigzagConversion.convert2("AHRTULWR", 2));
    //System.out.println(ZigzagConversion.convert2("PAYPALISHIRING", 3));
    // PAHNAPLSIIGYIR
    // PAHNAPLSIIGYIR
    System.out.println(ZigzagConversion.indexBased("PAYPALISHIRING", 4));
    // PINALSIGYAHRPI
    // PINALSIGYAHRPI

    //System.out.println(ZigzagConversion.convert2("PAYPALISHIRING", 5));
    // PHASIYIRPLIGAN
    // PHASIYIRPLIGAN
  }

  /*
    -----2------
    0 = 0
        2
        4
        6

    1 = 1
        3
        5
        7
        9
=
    A R U W
    H T L R

    -----3------
    0 = 0
        4
        8
        12

    1 = 1
        3
        5
        7
        9
        11
        13

    2 = 2
        6
        10

    P   A   H   N
    A P L S I I G
    Y   I   R

    -----4------
    0 = 0
        6
        12

    1 = 1
        5
        7
        11
        13

    2 = 2
        4
        8
        10
        14
        16

    3 = 3
        9
        15

    P     I    N
    A   L S  I G
    Y A   H R
    P     I


    -----5------

    0 = 0
        8
        16

    1 = 1
        7
        9
        15
        17

    2 = 2
        6
        10
        14
        18

    3 = 3
        5
        11
        13

    4 = 4
        12
        20

    H       D       Y
    E     T B     S H
    L   R   J   W   F
    L H     K Q
    O       P



    -----6------

    0 = 0
        10
        20

    1 = 1
        9
        11
        19
        21

    2 = 2
        8
        12
        18
        22

    G     Q     Z
    F    WH    CJ
    Y   X O   T U
    E  S  J  Q  T
    W N   L R
    U     Q
     */

  public static String indexBased(String s, int numRows) {

    int length = s.length();

    if (length < 3) {
      return s;
    } else if (numRows < 2) {
      return s;
    } else if (numRows == length) {
      return s;
    }

    StringBuilder result = new StringBuilder(s.length());

    for (int row = 0; row < numRows; row++) {
      for (int i = 0; i < length; i++) {
        int offset = offset(row, i, numRows);
        if (offset >= length) {
          break;
        }
        result.append(s.charAt(offset));
      }
    }

    return result.toString();
  }

  private static int offset(int row, int index, int numRows) {

    if (index == 0) {
      return row;
    } else if (numRows == 2) {
      return row + (2 * index);
    }

    int lastRow = numRows - 1;

    if (row == 0 || row == lastRow) {
      int stepFactor = 2 + (2 * (numRows - 2));
      return row + (stepFactor * index);
    }
    if (index % 2 != 0) {
      int step = (numRows - 1 - row) * 2;
      return offset(row, index - 1, numRows) + step;
    }
    int step = row * 2;
    return offset(row, index - 1, numRows) + step;
  }

  public static String matrixBased(String s, int numRows) {

    int length = s.length();

    if (length < 3) {
      return s;
    } else if (numRows < 2) {
      return s;
    } else if (numRows == length) {
      return s;
    }

    int maxColumns = (length / 2) + 1;
    var matrix = new char[numRows][maxColumns];

    final int modeFill = 1;
    final int modeZigzag = 2;

    int mode = modeFill;
    int usedColumn = 0;

    for (int i = 0, row = 0, lastRow = numRows - 1; i < length; i++) {

      char next = s.charAt(i);

      if (mode == modeFill) {
        matrix[row][usedColumn] = next;
        if (row == lastRow) {
          mode = modeZigzag;
          usedColumn++;
        } else {
          row++;
        }
      } else {
        if (row == 1) {
          matrix[0][usedColumn] = next;
          mode = modeFill;
        } else {
          matrix[--row][usedColumn] = next;
          usedColumn++;
        }
      }
    }

    StringBuilder result = new StringBuilder(s.length());
    char[] rowChars = matrix[0];

    for (int column = 0, row = 0, lastRow = numRows - 1, lastColumn = usedColumn + 1;;) {
      char next = rowChars[column++];
      if (next != '\u0000') {
        result.append(next);
      }
      if (column == lastColumn) {
        if (row == lastRow) {
          break;
        }
        rowChars = matrix[++row];
        column = 0;
      }
    }

    return result.toString();
  }
}
