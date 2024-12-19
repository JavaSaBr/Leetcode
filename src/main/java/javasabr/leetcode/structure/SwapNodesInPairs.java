package javasabr.leetcode.structure;

import javasabr.leetcode.ListNode;

public class SwapNodesInPairs {

    public static void main(String[] args) {
        System.out.println(ListNode.values(baseline(ListNode.build(1, 2, 3, 4))));
        System.out.println(ListNode.values(baseline(ListNode.build(1))));
        System.out.println(ListNode.values(baseline(ListNode.build(1, 2, 3))));
    }

    public static ListNode baseline(ListNode head) {
        if (head == null) {
            return null;
        }
        return swap(null, head, 1);
    }

    // [{1},2,3,4] 1
    // [1,{2},3,4] 2
    // [{2},1,3,4] 2*
    // [2,1,{3},4] 3
    // [2,1,3,{4}] 4
    // [2,1,{4},3] 4*

    public static ListNode swap(ListNode prev, ListNode current, int level) {
        if (current == null) {
            return prev;
        } else if (level % 2 != 0) {
            ListNode resultNext = swap(current, current.next, level + 1);
            if (prev != null) {
                prev.next = resultNext;
            }
            return resultNext;
        } else {
            ListNode originalNext = current.next;
            prev.next = originalNext;
            current.next = prev;
            swap(prev, originalNext, level + 1);
            return current;
        }
    }
}
