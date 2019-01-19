/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val,Node _next) {
        val = _val;
        next = _next;
    }
};
*/

/*
    This one had some really hard to understand test cases because of the way the list is represented.  
    It can be broken down into 4 different cases, the current node is smaller than the
    value and the next node is smaller than the value or the current node is larger than the value and the next node is larger, 
    this means we are either moving on through the list or we may be at the end, check whether the next is smaller than the current if so, 
    our node needs to be inserted here. Else, we need to find the correct spot to put the node where
    it fits nicely between curr and next.  The edge cases, are empty list or one node

*/
class Solution {
    public Node insert(Node head, int insertVal) {
	// #1. for empty list just set self referencing new node as head
    if(head==null){
        head = new Node();
        head.val=insertVal;
        head.next=head;
        return head;
    }
    
    Node p = head;
	// #2 if list has one item only 
    if(p.next!=head) {
		// for case 3, find where cycle ends
        while (p.next!=head && p.val <= p.next.val) {
            p = p.next;
        }
		// happy case #4
        if (p.val > insertVal && p.next.val<insertVal) {
            while ((!(p.val <= insertVal && insertVal <= p.next.val))) {
                p = p.next;
            }
        }
    }
	// now add the new node
    p.next = new Node(insertVal, p.next);

    return head;
    }
}