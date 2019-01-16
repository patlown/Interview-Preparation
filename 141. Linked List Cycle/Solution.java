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
    Common two pointer approach, keep a fast and slow, if they ever meet,
    we know we have a cycle.  If fast reaches end of list, no cycle.
    O(n) time, O(1) space.
    Complexity analysis when the list has a cycle:
    Break down the movement of the slow pointer into two steps, the non-cyclic part
    and the cyclic part:
    1. The slow pointer takes "non-cyclic length" steps to enter the cycle.  At this point
    , the fast pointer has already reached the cycle. Number of iterations = non-cyclic length
    = N
    2. Both pointers are now in the cycle.  The fast runner moves two steps
    while the slow runner moves 1 step at a time.  Since the speed difference
    is 1, it takes (distance between two runners)/(difference of speed) loops for the fast runner to catch up with the slow runner.
    The distance is at most "cyclic length K" and the speed difference is 1, we 
    can conclude that the Number of iterations = ~K

    Thus, worst case time complexity is O(N+K) which is O(n)
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        //check if we have empty list or one element list with no next
        if(head == null || head.next == null) return false;
        ListNode fast = head.next.next, slow = head;
        //slow will always be behind fast so no need to check for it to be null
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow) return true;
        }
        return false;
    }
}

/*
    We have super trivial solution where we use a hashset to keep track of nodes we have
    and return true if we see a node we have previously visited.  This uses O(n) time
    as well as O(n) space

*/

/*
    Dummy node approach, iterate through every node setting each node's next
    equal to a dummy node, if we encounter a dummy node through traversal, the
    list has a cycle.  Make dummy node point to itself for case where we have 
    single node cyclic list
*/
public class Solution {
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = dummy;
        while(head != null){
            if(head.next == dummy) return true;
            ListNode next = head.next;
            head.next = dummy;
            head = next;
        }
        return false;
    }
}



