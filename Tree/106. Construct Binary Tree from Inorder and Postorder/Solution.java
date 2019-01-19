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
    Same idea as 105. this time however, we have to build the right subtree
    before we build the left subtree as the postorder is given to us in the
    reverse order.  We start by making the root equal to the last element in
    the postorder array and then decrementing the postindex as we move through
    the recursion.  
 */
class Solution {
    int postIndex;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        
        if(inorder == null || inorder.length == 0) return null;
        
        Map<Integer,Integer> map = new HashMap();
        for(int i =0; i < inorder.length; i++){
            map.put(inorder[i],i);
        }
        postIndex = postorder.length-1;
        
        return btHelper(postorder,map,0,postIndex);
        
        
    }
    
    private TreeNode btHelper(int[] postorder,Map<Integer,Integer> map, int start, int end){
        
        if(start > end) return null;
        
        TreeNode root = new TreeNode(postorder[postIndex--]);
        
        int index = map.get(root.val);
        
        root.right = btHelper(postorder,map,index+1,end);
        root.left = btHelper(postorder,map,start,index-1);
        
        return root;
    }
}