package javasabr.leetcode;

import java.util.ArrayList;
import java.util.List;

public class ListNode {
  public int val;
  public ListNode next;

  public static ListNode build(int... values) {

    ListNode head = new ListNode(values[0], null);
    ListNode next = head;

    for (int i = 1; i < values.length; i++) {
      var node = new ListNode(values[i], null);
      next.next = node;
      next = node;
    }

    return head;
  }

  public static List<Integer> values(ListNode head) {

    if (head == null) {
      return List.of();
    }

    var result = new ArrayList<Integer>();
    var next = head;

    while (next != null) {
      result.add(next.val);
      next = next.next;
    }

    return result;
  }

  public ListNode() {
  }

  public ListNode(int val) {
    this.val = val;
  }

  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }

    @Override
    public String toString() {
        return "{" + "val=" + val + ", next=" + (next == null ? "null" : next.val) + '}';
    }
}
