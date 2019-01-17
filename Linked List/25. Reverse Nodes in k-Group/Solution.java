/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

 /*
    This approach is similar to iteratively reversing a linked list, however,
    this time we need to keep track of how many nodes we reverse.  Keep a last pointer,
    a curr pointer and an end pointer to reverse the list in place.  There are some
    edge cases that are easy to pick up through thinking about input (0 or 1 nodes).  There are also
    edge cases that can only be seen when working through examples.  The fix for setting
    the last node in reversed list to last node in next non-reversed list, changing the head
    to point to the correct node and determining when it is appropriate to reverse a list.
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {

        if(head == null || head.next == null) return head;
        
        ListNode curr = head,start, end, last;
        do{
            start = curr;
            int steps = 0;
            while(steps < k){
                if(curr == null) break;
                curr = curr.next;
                steps++;
                //make sure head is correctly placed at end of first reversed list, make k is not greater than length of list
                if(steps == k -1 && start == head && curr != null) head = curr;
            }
            last = curr;
            end = curr;
            //if curr is null we broke our loop early unless steps is k then the current list is exactly size k
            if(curr != null || steps == k) reverseList(start,last,end,k);
            
        }while(end != null);
        
        return head;
    }
    
    public void reverseList(ListNode start, ListNode last, ListNode end, int k){
        ListNode curr = start;
        while(curr != end){
            ListNode temp = curr.next;
            curr.next = last;
            /*
            tricky part, the last node in the reversed list should point to last node in next unreversed list (which becomes first after reversal)
            however, this is only the case if we have enough nodes in next list to be reversed
            */
            if(curr.next == end){
                //set last element in reversed list to be first element in next reversed list
                ListNode fix = curr;
                while(k > 0){
                    if(fix == null) break;
                    fix = fix.next;
                    k--;
                }
                if(k == 0 && fix != null) curr.next = fix;
            }
            last = curr;
            curr = temp;
        }
    }
}