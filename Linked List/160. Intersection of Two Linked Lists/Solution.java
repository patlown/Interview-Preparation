/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
 /*
    Trivial hashset solution, keep track of all nodes in first list,
    traverse second list checking if we've seen this node before
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;
        HashSet<ListNode> seen = new HashSet();
        ListNode currA = headA;
        while(currA != null){
            seen.add(currA);
            currA = currA.next;
        }
        ListNode currB = headB;
        while(currB != null){
            if(seen.contains(currB)) return currB;
            currB = currB.next;
        }
        
        return null;
    }
}

/*
    We could also do a dummy node solution that works much like the hashset solution
    but only require O(1) space.  However, the question states we must not destroy
    either list so this will not work
*/


/*
    Two pointer approach: iterate pointer through each list one step at a time.
    When the first pointer reaches the end of the list, keep track of how man steps it takes
    for the second pointer to reach the end (call this k).  Start first pointer
    at the head of the first list, start second pointer k steps into the second list.
    Thus, iterating through the lists again will yield in an intersection between the two
    pointers at the start of the intersection of the lists (if it exists).  Else each will reach the end
    of their lists and return null.
*/
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;
        
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        
        while(ptrA != null && ptrB != null){
            ptrA = ptrA.next;
            ptrB = ptrB.next;
        }
        ListNode longer, shorter;
        
        if((ptrA == null && ptrB == null) || ptrA == null){
            shorter = headA;
            longer = headB;
            while(ptrB != null){
                ptrB = ptrB.next;
                longer = longer.next;
            }
        }else{
            shorter = headB;
            longer = headA;
            while(ptrA != null){
                ptrA = ptrA.next;
                longer = longer.next;
            }
        }

        while(longer != null && shorter != null){
            if(longer == shorter) return longer;
            longer = longer.next;
            shorter=shorter.next;
        }
        
        return null;
    }
}

/*
    Another two pointer approach: Start p on headA and q on headB, when a ptr reaches
    the end of its list, start it on the head of the other ptr's list.  This works
    similarly to the other approach as the shorter list pointer will be swapped to the longer
    list and incremented the correct amount when the longer list pointer shifts
    to the shorter list.  If they meet, it will be at the start of the intersection,
    if they don't, they will hit null at the same time.

*/
public class Solution {
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    ListNode p = headA;
    ListNode q = headB;
    while(p != q){
        p = (p == null)?(p = headB):(p = p.next);
        q = (q == null)?(q = headA):(q = q.next);            
    }
    return p;
}
