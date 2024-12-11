package javasabr.leetcode.number;

public class IntegerToRoman {

    public static void main(String[] args) {
        System.out.println(intToRoman(3749));
        System.out.println(intToRoman(58));
        System.out.println(intToRoman(10));
        System.out.println(intToRoman(100));
        System.out.println(intToRoman(1000));
    }

    private static String[][] simpleTable;

    static {

        String[][] table = new String[3][11];
        // level 0
        table[0][1] = "I";
        table[0][2] = "II";
        table[0][3] = "III";
        table[0][4] = "IV";
        table[0][5] = "V";
        table[0][6] = "VI";
        table[0][7] = "VII";
        table[0][8] = "VIII";
        table[0][9] = "IX";
        table[0][10] = "X";
        // level 1
        table[1][1] = "X";
        table[1][2] = "XX";
        table[1][3] = "XXX";
        table[1][4] = "XL";
        table[1][5] = "L";
        table[1][6] = "LX";
        table[1][7] = "LXX";
        table[1][8] = "LXXX";
        table[1][9] = "XC";
        table[1][10] = "X";
        // level 2
        table[2][1] = "C";
        table[2][2] = "CC";
        table[2][3] = "CCC";
        table[2][4] = "CD";
        table[2][5] = "D";
        table[2][6] = "DC";
        table[2][7] = "DCC";
        table[2][8] = "DCCC";
        table[2][9] = "CM";

        IntegerToRoman.simpleTable = table;
    }

    /*


Symbol	Value
I	1
V	5
X	10
L	50
C	100
D	500
M	1000

    Input: num = 3749

    Output: "MMMDCCXLIX"

    Explanation:

    3000 = MMM as 1000 (M) + 1000 (M) + 1000 (M)
     700 = DCC as 500 (D) + 100 (C) + 100 (C)
      40 = XL as 10 (X) less of 50 (L)
       9 = IX as 1 (I) less of 10 (X)
    Note: 49 is not 1 (I) less of 50 (L) because the conversion is based on decimal places
     */

    /**
     * @param num 1 <= num <= 3999
     */
    public static String intToRoman(int num) {

        if (num < 11) {
            return simpleTable[0][num];
        }

        var result = new StringBuilder();

        if (num >= 1000) {
            int mCount = num / 1000;
            for (int i = 0; i < mCount; i++) {
                result.append('M');
            }
        }

        if (num >= 100) {
            int mod = num % 1000;
            int count = mod / 100;
            String value = simpleTable[2][count];
            if (value != null) {
                result.append(value);
            }
        }

        int mod = num % 100;
        int count = mod / 10;
        String value = simpleTable[1][count];
        if (value != null) {
            result.append(value);
        }

        mod = num % 10;
        value = simpleTable[0][mod];

        if (value != null) {
            result.append(value);
        }

        return result.toString();
    }
}
