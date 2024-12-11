package javasabr.leetcode.string;

import java.util.ArrayDeque;
import java.util.BitSet;

public class LongestSubstringWithoutRepeatingCharacters {

    public static void main(String[] args) {

    }

    public static int lengthOfLongestSubstring(String s) {

        var substring = new ArrayDeque<Character>(20);
        var uniqSet = new BitSet(100);
        int maxSubstringLength = 0;

        for (int i = 0, length = s.length(); i < length; i++) {

            char next = s.charAt(i);

            if (uniqSet.get(next)) {
                maxSubstringLength = Math.max(substring.size(), maxSubstringLength);
                for (char removed = substring.removeFirst(); removed != next; removed = substring.removeFirst()) {
                    uniqSet.clear(removed);
                }
            }

            substring.addLast(next);
            uniqSet.set(next, true);
        }

        maxSubstringLength = Math.max(substring.size(), maxSubstringLength);

        return maxSubstringLength;
    }
}
