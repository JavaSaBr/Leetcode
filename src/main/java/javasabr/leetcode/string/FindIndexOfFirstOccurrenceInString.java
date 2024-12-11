package javasabr.leetcode.string;

public class FindIndexOfFirstOccurrenceInString {

    public static void main(String[] args) {

    }

    public static int strStr(String haystack, String needle) {
        if (haystack.length() < needle.length()) {
            return -1;
        }

        if (needle.length() == 1) {
            char firstChar = needle.charAt(0);
            for (int i = 0, length = haystack.length(); i < length; i++) {
                if (firstChar == haystack.charAt(i)) {
                    return i;
                }
            }
            return -1;
        }

        if (needle.length() == 2) {
            char firstChar = needle.charAt(0);
            char secondChar = needle.charAt(1);
            for (int i = 0, length = haystack.length() - 1; i < length; i++) {
                if (firstChar == haystack.charAt(i) && secondChar == haystack.charAt(i + 1)) {
                    return i;
                }
            }
            return -1;
        }

        char firstChar = needle.charAt(0);
        char secondChar = needle.charAt(1);
        char thirdChar = needle.charAt(2);

        for (int i = 0, length = haystack.length() - 2; i < length; i++) {

            if (!(firstChar == haystack.charAt(i) && secondChar == haystack.charAt(i + 1)
                    && thirdChar == haystack.charAt(i + 2))) {
                continue;
            }

            int haveChars = haystack.length() - i;
            if (needle.length() > haveChars) {
                return -1;
            }

            boolean isOk = true;
            for (int j = 3, max = needle.length(); j < max; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    isOk = false;
                    break;
                }
            }

            if (isOk) {
                return i;
            }
        }
        return -1;
    }
}
