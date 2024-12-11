package javasabr.leetcode.string;

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {

    public static void main(String[] args) {

    }

    public static boolean isValid(String s) {

        int length = s.length();
        if (length == 1) {
            return false;
        }

        Deque<Character> active = new ArrayDeque<>();

        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '[', '(', '{': {
                    active.add(ch);
                    break;
                }
                case ')': {
                    if (active.isEmpty()) {
                        return false;
                    }
                    Character opened = active.removeLast();
                    if (opened != '(') {
                        return false;
                    }
                    break;
                }
                case ']': {
                    if (active.isEmpty()) {
                        return false;
                    }
                    Character opened = active.removeLast();
                    if (opened != '[') {
                        return false;
                    }
                    break;
                }
                case '}': {
                    if (active.isEmpty()) {
                        return false;
                    }
                    Character opened = active.removeLast();
                    if (opened != '{') {
                        return false;
                    }
                    break;
                }
            }
        }

        return active.isEmpty();
    }
}
