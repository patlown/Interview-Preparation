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
    Simple DFS/Recursive approach, traverse the trees simultaneosly and follow just
    three rules.  If both tree nodes are null, return null, if both have values, add them
    together and merge their left and right subtrees, if exactly one node is null,
    return the other ones subtree.  
 */
class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return t1;
        if(t1 != null && t2 != null){
            t1.val += t2.val;
            t1.left = mergeTrees(t1.left,t2.left);
            t1.right = mergeTrees(t1.right,t2.right);
            return t1;
        }
        return t1 == null ? t2 : t1;
        
    }
}
 /*
    Slightly more optimized above approach
 */
public class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    if (t1 != null) {
        t1.val = (t2 == null) ? t1.val : (t1.val + t2.val);            
        t1.left = (t2 == null) ? t1.left : mergeTrees(t1.left, t2.left);
        t1.right = (t2 == null) ? t1.right : mergeTrees(t1.right, t2.right);
        return t1;
    } else {
        return t2;
    }
}
}