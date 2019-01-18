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
    Simple stack solution.  Go down left subtree of current node pushing all
    nodes to the stack, when we pop a node from the stack, we we add its right node's left
    subtree to the stack.  We know we don't have a next if our stack is empty.
 */
class BSTIterator {
    
    Stack<TreeNode> stack;
    TreeNode root;
    public BSTIterator(TreeNode root) {
        this.root = root;
        stack = new Stack();
        fillStack(root);
    }
    
    private void fillStack(TreeNode curr){
        while(curr != null){
            stack.push(curr);
            curr = curr.left;
        }
    }
    
    /** @return the next smallest number */
    public int next() {
        TreeNode res = stack.pop();
        fillStack(res.right);
        return res.val;
    }
    
    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return !stack.empty();
    }
}

/**
 * Your BSTIterator object will be instantiated and called as such:
 * BSTIterator obj = new BSTIterator(root);
 * int param_1 = obj.next();
 * boolean param_2 = obj.hasNext();
 */