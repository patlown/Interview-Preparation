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
    Bfs solution: poll from q, invert children, then add children to q
 */
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        Queue<TreeNode> q = new LinkedList();
        q.add(root);
        while(!q.isEmpty()){
            TreeNode curr = q.poll();
            TreeNode temp = curr.left;
            curr.left = curr.right;
            curr.right = temp;
            if(curr.left != null) q.add(curr.left);
            if(curr.right!=null) q.add(curr.right);
        }
        
        return root;
        
    }
}
/*
    Simple DFS recursive approach, invert children, then recurse
*/
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
}