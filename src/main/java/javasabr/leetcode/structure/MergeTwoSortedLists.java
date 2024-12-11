package javasabr.leetcode.structure;

import javasabr.leetcode.ListNode;

public class MergeTwoSortedLists {

    public static void main(String[] args) {

    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        if (list1 == null && list2 == null) {
            return null;
        }

        if (list1 != null && list2 == null) {
            return list1;
        } else if (list1 == null) {
            return list2;
        }

        int init1 = list1.val;
        int init2 = list2.val;

        ListNode head;
        ListNode next1;
        ListNode next2;

        if (init1 < init2) {
            head = new ListNode(init1);
            next1 = list1.next;
            next2 = list2;
        } else {
            head = new ListNode(init2);
            next1 = list1;
            next2 = list2.next;
        }

        ListNode toExtend = head;

        while (true) {

            if (next1 != null && next2 == null) {
                toExtend.next = new ListNode(next1.val);
                toExtend = toExtend.next;
                next1 = next1.next;
                continue;
            } else if (next2 != null && next1 == null) {
                toExtend.next = new ListNode(next2.val);
                toExtend = toExtend.next;
                next2 = next2.next;
                continue;
            }

            if (next2 == null) {
                break;
            }

            int next1Val = next1.val;
            int next2Val = next2.val;

            if (next1Val < next2Val) {
                toExtend.next = new ListNode(next1Val);
                toExtend = toExtend.next;
                next1 = next1.next;
            } else {
                toExtend.next = new ListNode(next2Val);
                toExtend = toExtend.next;
                next2 = next2.next;
            }
        }

        return head;
    }
}
