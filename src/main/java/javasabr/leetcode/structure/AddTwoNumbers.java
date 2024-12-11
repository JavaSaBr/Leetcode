package javasabr.leetcode.structure;

import javasabr.leetcode.ListNode;

import java.util.Arrays;

public class AddTwoNumbers {

    public static void main(String[] args) {

    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        var left = extractNumbers(l1);
        var right = extractNumbers(l2);
        var sum = sumArrays(left, right);

        return buildNodes(sum);
    }

    private static int[] extractNumbers(ListNode node) {

        int count = 0;

        for (var next = node; next != null; next = next.next) {
            count++;
        }

        int[] array = new int[count];
        int j = 0;

        for (var next = node; next != null; next = next.next) {
            array[j++] = next.val;
        }

        return array;
    }

    private static int[] sumArrays(int[] left, int[] right) {

        int[] result = new int[Math.max(left.length, right.length)];
        int extraNumber = -1;

        for (int i = 0, length = result.length, leftLimit = left.length, rightLimit = right.length; i < length; i++) {

            int leftValue = i < leftLimit ? left[i] : 0;
            int rightValue = i < rightLimit ? right[i] : 0;
            int currentValue = result[i];
            int resultValue = currentValue + leftValue + rightValue;

            if (resultValue <= 9) {
                result[i] = resultValue;
            } else {
                result[i] = resultValue % 10;

                if (i < length - 1) {
                    int parentOffset = 1;
                    while (true) {
                        int parentIndex = i + parentOffset;
                        if (parentIndex >= length) {
                            extraNumber = 1;
                            break;
                        }
                        int parentValue = result[parentIndex];
                        if (parentValue == 9) {
                            result[parentIndex] = 1;
                            parentOffset++;
                        } else {
                            result[parentIndex] = parentValue + 1;
                            break;
                        }
                    }
                } else {
                    extraNumber = 1;
                }
            }
        }

        if (extraNumber == -1) {
            return result;
        }

        int[] extended = Arrays.copyOf(result, result.length + 1);
        extended[extended.length - 1] = extraNumber;

        return extended;
    }

    public static ListNode buildNodes(int[] values) {
        ListNode head = new ListNode(values[0]);
        ListNode next = head;
        for (int i = 1; i < values.length; i++) {
            next.next = new ListNode(values[i]);
            next = next.next;
        }
        return head;
    }
}
