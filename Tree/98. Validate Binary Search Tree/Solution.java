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
    First thought: Do an inorder traversal and check that the current node is always bigger than
    its previous.  We can keep a stack as we wwill need more than just the previous node to check validity.
    Since we are iterating in order, we jsut need to check the previous node in order is smaller
    than our current node.
    Edges cases: if we have 0 or 1 nodes, that is by definition a valid BST.  What if two of the same
    values exist in the tree, is that invalid? If so, check that the previous node is not equal
    to the current node.

 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)) return true;
        Stack<TreeNode> stack = new Stack();
        TreeNode curr = root;
        //fill up stack with all left children of root node
        fillStack(curr,stack);
        
        int flag = 0;
        int prev = stack.peek().val;
        
        while(!stack.empty()){
            curr = stack.pop();
            if(curr.val <= prev){
                if(flag != 0) return false;
                flag = 1;
            } 
            prev = curr.val;
            fillStack(curr.right,stack);
        }
        
        return true;
        
    }
    //simple stack filling method, adds current node and its left subtree
    private void fillStack(TreeNode node, Stack<TreeNode> stack){
        while(node != null){
            stack.push(node);
            node = node.left;
        }
    }
}
/*
    More elegant recursive solution.  Does not do an inorder traversal, just simply checks that the
    nodes are within their respective bounds.  We keep track of a the min value and the max value
    possible for any current node.  A binary tree is valid if its root's subtrees are valid and every value
    in the left subtree is less than the root and every value in the right subtree is greater than
    the root.  Start with a fully bound min and max using Long.MIN and Long.Max.  Check that the root
    falls within this range, then check its left subtree by considering that the min is the same but now
    the max is the current root and similarly, the right subtree has the same max bu the min is now the current
    root.
*/
class Solution {
    public boolean isValidBST(TreeNode root) {
        return isValidBST (root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean isValidBST (TreeNode root, long min, long max)    {
        if (root == null) return true;
        return (root.val > min &&
                root.val < max &&
                isValidBST(root.left, min, root.val) &&
                isValidBST(root.right, root.val, max));
    }
}


