package javasabr.leetcode.number;

public class RomanToInteger {

    public static void main(String[] args) {
    }

    public static int romanToInt(String s) {
        int offset = 60;
        int[] symbolMapping = new int[30];
        symbolMapping['I' - offset] = 1;
        symbolMapping['V' - offset] = 5;
        symbolMapping['X' - offset] = 10;
        symbolMapping['L' - offset] = 50;
        symbolMapping['C' - offset] = 100;
        symbolMapping['D' - offset] = 500;
        symbolMapping['M' - offset] = 1000;

        boolean[] needSubtraction = new boolean[30];
        needSubtraction['V' - offset] = true;
        needSubtraction['X' - offset] = true;
        needSubtraction['L' - offset] = true;
        needSubtraction['C' - offset] = true;
        needSubtraction['D' - offset] = true;
        needSubtraction['M' - offset] = true;

        char[] rawString = s.toCharArray();

        int result = 0;

        for (int i = 0, length = rawString.length, maxNext = length - 1; i < length; i++) {

            char currentSymbol = rawString[i];
            int value = symbolMapping[currentSymbol - offset];

            if (!(currentSymbol == 'I' || currentSymbol == 'X' || currentSymbol == 'C')) {
                result += value;
                continue;
            }

            char nextSymbol = i < maxNext ? rawString[i + 1] : 'U';

            if (currentSymbol == nextSymbol || !needSubtraction[nextSymbol - offset]) {
                result += value;
                continue;
            }

            int nextValue = symbolMapping[nextSymbol - offset];

            if (nextValue < value) {
                result += value;
                continue;
            }

            result += (nextValue - value);
            i++;
        }

        return result;
    }
}
