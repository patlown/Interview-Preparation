/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */

 /*
    This solution is much like the one used in question 141, however this time we
    must return the node that starts the cycle.  We can do the same Dummy node 
    approach where we set every node's next to the dummy node and the first time
    we see that our current node is pointing to the dummy node, we can then
    just return the current node
    O(n) time, O(1) space
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head == null) return head;
        if(head.next == null || head.next == head) return head.next;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        while(head != null){
            if(head.next == dummy) return head;
            ListNode next = head.next;
            head.next = dummy;
            head = next;
        }
        return head;
    }
}
/*
    We have the trivial HashSet approach where we keep track of visited nodes
    and return the node the first time we see it again.
    O(n) time, O(1) space
*/

/*
    Two Pointer approach:  We can use a fast and slow pointer approach where,
    if they meet, we know that there is a cycle, when they meet they are N steps from
    the head of the cycle and the head is N steps from the start of the cycle.  Thus, we can
    just increment slow and a new pointer from head one step at a time to have them meet
    at the start of the cycle.  If there is no cycle, fast will hit null and we'll return
*/
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head == null) return head;
        if(head.next == null || head.next == head) return head.next;
        
        ListNode fast = head;
        ListNode slow = head;
        
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                ListNode curr = head;
                while(curr != slow){
                    curr = curr.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        return null;
    }
}