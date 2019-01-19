/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */

 /*
    This approach iteratively moves through the old list and creates a copy
    of the old node and adds it to the new list. It keeps a map of previously visited nodes
    where the key is the old node and the value is the new node.  Each iteration makes three checks
    the first check is whether the current old node is in the map if it is make the
    new young node point to its value, else create new node and add it to map.  Then,
    check if current old node's random is in the map, ..., then check if current old node's
    next is in the map, ... . When we have reached the end of the old list, we are done.
    O(n) time, O(n) space
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null) return head;
        Map<RandomListNode,RandomListNode> map = new HashMap();
        map.put(head,new RandomListNode(head.label));
        //pointer to new head of random list copy
        RandomListNode newHead = map.get(head);
        //pointers for iterating young list and old list
        RandomListNode young = map.get(head);
        RandomListNode old = head;
        
        while(old != null){
            //add the old node to young list if it doesn't exist
            if(map.containsKey(old)) young = map.get(old);
            else{
                young = new RandomListNode(old.label);
                map.put(old,young);
            }
            
            //add the old node's random to the young list
            //maps can contains a null key and we will make use of that in this solution
            if(map.containsKey(old.random)) young.random = map.get(old.random);
            else{
                if(old.random != null){
                    young.random = new RandomListNode(old.random.label);
                    map.put(old.random, young.random);
                }
            }
            
            //add the old node's next to the young list
            if(map.containsKey(old.next)) young.next = map.get(old.next);
            else{
                if(old.next != null){
                    young.next = new RandomListNode(old.next.label);
                    map.put(old.next,young.next);
                }
            }
            
            old = old.next;
        }
        
        return newHead;
        
    }
}
/*
    DFS/BFS Approach: We could also treat this as a graph problem and build a copy of it in a DFS/BFS fashion.
    In this algorithm, we would need to keep a set of visited nodes so that we can reuse them
    if we come across the same node twice. 
    O(N) space, O(N) time
*/
/*
    Iterative approach with O(1) space: This solution creates an interleaving of new nodse
    and old nodes and then fixes the the new list to be correct.  It can be broken down into
    3 parts : 1. build the interleaving list by iterating through the old nodes next pointers.
    2. iterate through list again, this time fixing the new node's to point to their random counterparts.
    3. iterate through list, decoupling clone list from old list and reconstructing the old list.
    O(n) time, O(1) space since we don't keep a visited list
*/
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        
        if(head == null) return head;
        RandomListNode curr = head;
        //1. build interleaving list
        while(curr != null){
            RandomListNode temp = curr.next;
            curr.next = new RandomListNode(curr.label);
            curr.next.next = temp;
            curr = curr.next.next;
        }
        //2.connect random pointers to new nodes
        RandomListNode old = head;
        RandomListNode young = head.next;
        while(old != null){
            if(old.random != null) young.random = old.random.next;
            if(young.next != null) young = young.next.next;
            old = old.next.next;
        }
        //3. decouple the lists
        RandomListNode clone = head.next;
        young = clone;
        old = head;
        //young always stays ahead of old so we can safely only rely on the condition of young being null.
        while(young != null){
            old.next = old.next.next;
            old = old.next;
            if(young.next != null){
                young.next = young.next.next;
            }
            young = young.next;
        }
        return clone;
    }
}