package javasabr.leetcode.string;

public class LongestPalindrome {

    public static void main(String[] args) {
        System.out.println(counterBased("aaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjkkkkkkkkkkllllllllllmmmmmmmmmmnnnnnnnnnnooooooooooppppppppppqqqqqqqqqqrrrrrrrrrrssssssssssttttttttttuuuuuuuuuuvvvvvvvvvvwwwwwwwwwwxxxxxxxxxxyyyyyyyyyyzzzzzzzzzzyyyyyyyyyyxxxxxxxxxxwwwwwwwwwwvvvvvvvvvvuuuuuuuuuuttttttttttssssssssssrrrrrrrrrrqqqqqqqqqqppppppppppoooooooooonnnnnnnnnnmmmmmmmmmmllllllllllkkkkkkkkkkjjjjjjjjjjiiiiiiiiiihhhhhhhhhhggggggggggffffffffffeeeeeeeeeeddddddddddccccccccccbbbbbbbbbbaaaa"));
    }

    public static String counterBased(String s) {

        int length = s.length();

        if (length < 2) {
            return s;
        } else {
            char first = s.charAt(0);
            char second = s.charAt(1);
            if (length == 2 && first == second) {
                return s;
            } else if (length == 3 && first == second && first == s.charAt(2)) {
                return s;
            }
        }

        int substringSize;
        int substringOffset;

        int longestOffset = 0;
        int longestSize = 0;

        for (int i = 0, exitTrigger = length; i < length && longestSize < exitTrigger; i++, exitTrigger = (length - i + 1) * 2) {

            char mid = s.charAt(i);

            substringOffset = i;
            substringSize = 1;

            // try to find symmetric [aaaaa]
            for (int j = i + 1; j < length; j++) {

                char next = s.charAt(j);

                if (mid != next) {
                    break;
                }

                substringSize++;
            }

            if (longestSize < substringSize) {
                longestOffset = substringOffset;
                longestSize = substringSize;
            }

            // if it can be [abCCba]
            if (substringSize >= 2) {

                substringSize = 2;

                // try to find symmetric [abCCba]
                for (int j = i - 1, k = i + 2; j >= 0 && k < length; j--, k++) {

                    char left = s.charAt(j);
                    char right = s.charAt(k);

                    if (left != right) {
                        break;
                    }

                    substringOffset--;
                    substringSize += 2;
                }
            }

            if (longestSize < substringSize) {
                longestOffset = substringOffset;
                longestSize = substringSize;
            }

            substringOffset = i;
            substringSize = 1;

            // try to find symmetric [abCba]
            for (int j = i - 1, k = i + 1; j >= 0 && k < length; j--, k++) {

                char left = s.charAt(j);
                char right = s.charAt(k);

                if (left != right) {
                    break;
                }

                substringOffset--;
                substringSize += 2;
            }

            if (longestSize < substringSize) {
                longestOffset = substringOffset;
                longestSize = substringSize;
            }
        }

        if (longestSize < 1) {
            return String.valueOf(s.charAt(0));
        }

        return s.substring(longestOffset, longestOffset + longestSize);
    }

    public static String bufferBased(String s) {
        int length = s.length();

        if (length < 2) {
            return s;
        } else {
            char first = s.charAt(0);
            if (length == 2 && first == s.charAt(1)) {
                return s;
            } else if (length == 3 && first == s.charAt(1) && first == s.charAt(2)) {
                return s;
            }
        }

        var substringBuffer = new char[length * 2];
        int substringSize = 0;
        int substringCenter = substringBuffer.length / 2;
        int substringOffset = substringCenter;

        var altSubstringBuffer = new char[length * 2];
        int altSubstringSize = 0;
        int altSubstringOffset;

        var longest = new char[length];
        int longestSize = 0;

        for (int i = 0, altCheck = length - 3; i < length; i++) {

            char mid = s.charAt(i);

            if (substringSize > 0 && longestSize < substringSize + 1) {

                var hasTheSame = true;

                for (int x = substringOffset, limit = substringOffset + substringSize; x < limit; x++) {
                    if (substringBuffer[x] != mid) {
                        hasTheSame = false;
                        break;
                    }
                }

                if (hasTheSame) {
                    System.arraycopy(substringBuffer, substringOffset, longest, 0, substringSize);
                    longest[substringSize] = mid;
                    longestSize = substringSize + 1;
                }
            }

            substringOffset = substringCenter;
            substringBuffer[substringOffset] = mid;
            substringSize = 1;

            boolean foundSymmetric = false;

            // try to find symmetric [abCba]
            for (int j = i - 1, k = i + 1; j >= 0 && k < length; j--, k++) {

                char left = s.charAt(j);
                char right = s.charAt(k);

                if (left != right) {
                    break;
                }

                substringBuffer[--substringOffset] = left;
                substringSize++;
                substringBuffer[substringOffset + substringSize++] = right;
                foundSymmetric = true;
            }

            if (longestSize < substringSize) {
                System.arraycopy(substringBuffer, substringOffset, longest, 0, substringSize);
                longestSize = substringSize;
            }

            if (foundSymmetric) {
                substringOffset = substringCenter;
                substringSize = 0;
            }

            if (i > altCheck) {
                continue;
            }

            // try to find symmetric [abCCba]
            char secondMid = s.charAt(i + 1);

            if (secondMid != mid) {
                continue;
            }

            altSubstringOffset = substringCenter;
            altSubstringBuffer[substringOffset] = mid;
            altSubstringBuffer[substringOffset + 1] = secondMid;
            altSubstringSize = 2;

            // try to find symmetric [abCba]
            for (int j = i - 1, k = i + 2; j >= 0 && k < length; j--, k++) {

                char left = s.charAt(j);
                char right = s.charAt(k);

                if (left != right) {
                    break;
                }

                altSubstringBuffer[--altSubstringOffset] = left;
                altSubstringSize++;
                altSubstringBuffer[altSubstringOffset + altSubstringSize++] = right;
            }

            if (longestSize < altSubstringSize) {
                System.arraycopy(altSubstringBuffer, altSubstringOffset, longest, 0, altSubstringSize);
                longestSize = altSubstringSize;
            }
        }

        if (longestSize < 1) {
            return String.valueOf(s.charAt(0));
        }

        return String.copyValueOf(longest, 0, longestSize);
    }
}
