/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
/*
    First thought: Recurse down tree looking for smallest value that is equal
    to one of or between p and q.  If we reach a node that is not within our bounds
    we stop considering it.
    Upon Consideration: We can search for the larger node and the lowest value we 
    see that is withtin the range p(low) <= x <= q(hi).  This can end up returning
    high if that is the only node seen in range when we search.
    Expanding on above ^: We can follow 3 simple rules:
        1. If our current node is in range, it means that node p is in left subtree
        and node q is in right subtree so LCA is node.  If we go any deeper, we lose
        ancestor to one of the nodes.
        2. If our current node is less than low value, we have to search right
        3. If our current node is greater than high value, we have to search left.
    Implementation: This can be done with a simple recursive approach or we can do it
    iteratively, see recursive below ->
    O(logn) time in a balanced tree O(n) time in worst case
    O(logn) space in balanced tree O(n) space in worst case
*/
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        if(root == null) return null;
        TreeNode hi = p.val >= q.val ? p : q;
        TreeNode lo = p.val <= q.val ? p : q;
        
        if(root.val <= hi.val && root.val >= lo.val){
            return root;
        }else if(root.val < lo.val){
            return lowestCommonAncestor(root.right,p,q);
        }else{
            return lowestCommonAncestor(root.left,p,q);
        }
        
    }
}

/*
    Iterative solution that uses constant space:  We don't need to keep track of 
    past seen ancestors as we will return the first on we see.  Thus we can jsut
    iterate down the tree checking our rules.

    O(logn) time in a balanced tree O(n) in an unbalanced tree
    O(1) space
*/
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        TreeNode curr = root;
        TreeNode hi = p.val >= q.val ? p : q;
        TreeNode lo = p.val <= q.val ? p : q;
        
        while(curr != null){
            if(curr.val >= lo.val && curr.val <= hi.val){
                return curr;
            }else if(curr.val < lo.val){
                curr = curr.right;
            }else{
                curr = curr.left;
            }
        }
        
        return root;
    }
}