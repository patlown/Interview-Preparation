/* public class TreeNode {
    *     int val;
    *     TreeNode left;
    *     TreeNode right;
    *     TreeNode(int x) { val = x; }
    * }
    */
   class Solution {
       public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
           //edge cases, 0 or 1 nodes, return null
           if(root == null || (root.left == null && root.right == null)) return null;
           
           return helper(root,p,null);
       }
       
       private TreeNode helper(TreeNode root, TreeNode p, TreeNode suc){
           if(root == null) return null;
           
           if(root.val == p.val){
               //if the node has a right subtree, need to find the farthest left in it
               if(p.right != null) return findSuc(p);
               //if node does not have right subtree, return suc we've seen so far
               return suc;
           }else if(root.val > p.val){
               //if node is larger than value, this could potentially be a suc so save it 
               suc = root;
               return helper(root.left,p,suc);
           }
           //we have to move to our right, our current node can't be a suc to the value
           return helper(root.right,p,suc); 
       }
       //simple helper function to find farthest left in a node's right subtree
       private TreeNode findSuc(TreeNode p){
           if(p == null || p.right == null) return null;
           TreeNode curr = p.right;
           TreeNode res = curr;
           while(curr != null){
               res = curr;
               curr = curr.left;
           }
           
           return res;   
       }
   }