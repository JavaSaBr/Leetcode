package javasabr.leetcode.string;

public class LongestCommonPrefix {

    public static void main(String[] args) {

    }

    public static String longestCommonPrefix(String[] strs) {

        if (strs == null || strs.length == 0) {
            return "";
        } else if (strs.length == 1) {
            return strs[0];
        }

        String prefix = strs[0];

        if (prefix.isEmpty()) {
            return "";
        }

        for (int i = 1; i < strs.length; i++) {

            String another = strs[i];
            if (another.startsWith(prefix)) {
                continue;
            }

            for (int reduce = 1; reduce <= prefix.length(); reduce++) {

                String temp = prefix.substring(0, prefix.length() - reduce);
                if (temp.isEmpty()) {
                    return "";
                }

                if (another.startsWith(temp)) {
                    prefix = temp;
                    break;
                }
            }
        }

        return prefix;
    }
}
