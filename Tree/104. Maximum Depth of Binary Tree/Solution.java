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
    Simple DFS down to the bottom and return the max of either side
    O(n) time, O(n) space for call stack
*/

class Solution {
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}

/*
    BFS level order traversal where we keep track of the highest level seen
    Dummy node is used to keep track of levels
*/
class Solution {
    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        Queue<TreeNode> q = new LinkedList();
        TreeNode dummy = new TreeNode(0);
        q.add(root);
        q.add(dummy);
        int level = 1;
        while(!q.isEmpty()){
            TreeNode curr = q.poll();
            if(curr == dummy && !q.isEmpty()){
                level++;
                q.add(dummy);
            }
            if(curr.left != null) q.add(curr.left);
            if(curr.right != null) q.add(curr.right);  
        }
        return level;
    }
}