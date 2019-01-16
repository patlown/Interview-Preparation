/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

 /*
    Iterative first approach, beats 100%
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        
        //iterative approach, first thoughts
        
        //if list is empty or only has one element, return the original list
        if(head == null || head.next == null) return head;
        
        ListNode last = null;
        ListNode curr = head;
        
        while(curr != null){
            ListNode temp = curr.next;
            curr.next = last;
            last = curr;
            curr = temp; 
        }
        
        return last;
        
    }
}

/*
    Recursive follow up approach, very similar idea
*/
class Solution {
    public ListNode reverseList(ListNode head) {
        //base case: just return the node if it is that last
        if(head == null || head.next == null) return head;
        //assume our list is reversed from the head's next to the end
        ListNode p = reverseList(head.next);
        //we want the next node's next to us to reverse the list
        head.next.next = head;
        //make sure we set our current next to null to account for last node in the list
        //otherwise we will end up with a cycle
        head.next = null;
        //return the head of our reversed list which is p
        return p;
    }
}


