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
    Idea: Roots will c ome from the preorder traversal, so the root will be
    element 0, root's left may be element 1.  We know how many elements are
    in the current root's left subtree by keeping track of elements to its
    left in the inorder traversal and similarly, we know how many elements in
    root's right subtree by number of elements to its right in inorder.

    Implementation:  Build a map that maps the inorder element to its index
    for faster checking in recursive method.  The preorder elements will be 
    the new current roots in order.  Every time we place a root, we move our
    preorder index up by one.  We only build a root if there are elements available
    in the inorder array.  We can keep track of this using a start and end index
    so that we can simply return if we don't have any elements.

 */
class Solution {
    int preIndex = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        
        if(preorder == null || preorder.length == 0) return null;
        Map<Integer,Integer> map = new HashMap();
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i],i);
        }
        
        return btHelper(preorder,map,0,preorder.length-1);
        
        
    }
    
    private TreeNode btHelper(int[] preorder, Map<Integer,Integer> map, int start, int end){
        
        if(start > end) return null;
        
        TreeNode root = new TreeNode(preorder[preIndex++]);
        
        
        int index = map.get(root.val);
        
        root.left = btHelper(preorder,map,start,index-1);
        root.right = btHelper(preorder,map,index+1,end);
        
        return root;
        
    }
}