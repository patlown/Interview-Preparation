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
    Approach: Traverse the tree in a depth first search manner.  When you
    encounter a target node, return some boolean flag.  The flag helps determine if
    we found the desired nodes in any of the paths.  The LCA would then be the node
    for which both subtree recursions return a true.  It can also be the node which 
    itself is one of p or q and for which one of the subtree recursions returns true.
*/
class Solution {
    TreeNode ans;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root,p,q);
        return ans;
    }
    
    private boolean dfs(TreeNode root, TreeNode p, TreeNode q){
        if(root == null) return false;
        
        boolean left = dfs(root.left,p,q);
        boolean right = dfs(root.right,p,q);
        boolean mid = (root.val == p.val || root.val == q.val) ? true : false;
        
        if((left && right) || (left && mid) || (right && mid))
            ans = root;
        return left || right || mid;
        
    }
}