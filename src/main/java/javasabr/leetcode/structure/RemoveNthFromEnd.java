package javasabr.leetcode.structure;

import javasabr.leetcode.ListNode;

public class RemoveNthFromEnd {

  public static final int RETURN_NULL = -1;
  public static final int RETURN_HEAD_NEXT = -2;

  public static void main(String[] args) {
    System.out.println(ListNode.values(baseline(ListNode.build(1,2,3,4,5), 2)));
    System.out.println(ListNode.values(baseline(ListNode.build(1), 1)));
    System.out.println(ListNode.values(baseline(ListNode.build(1, 2), 1)));
    System.out.println(ListNode.values(baseline(ListNode.build(1, 2), 2)));
  }

  private static ListNode baseline(ListNode head, int n) {
    int pos = removeRecurr(null, head, n);
    return switch (pos) {
      case RETURN_NULL -> null;
      case RETURN_HEAD_NEXT -> head.next;
      default -> head;
    };
  }

  private static int removeRecurr(ListNode prev, ListNode current, int n) {

    if (current.next == null) {
      if (n == 1) {
        if (prev == null) {
          return RETURN_NULL;
        }
        prev.next = null;
      }
      return 1;
    }

    int nextPos = removeRecurr(current, current.next, n);
    if (nextPos < 0) {
      return nextPos;
    }

    int pos = nextPos + 1;

    if (pos == n) {
      if (prev == null) {
        return RETURN_HEAD_NEXT;
      }
      prev.next = current.next;
    }

    return pos;
  }
}
