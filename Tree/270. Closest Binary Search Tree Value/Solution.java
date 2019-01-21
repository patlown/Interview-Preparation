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
    Since we are searching for a double value in an integer tree, there is no chance that we could
    end up going down the wrong path.  Thus, we just need to search for the value in the tree.  This
    is a simple binary search where we keep track of the closest distance that we have seen.  When we
    get to the bottom of the tree we know that our closest distance is indeed the closest distance.
    This solution is O(logN) time and O(1) space.
 */
class Solution {
    public int closestValue(TreeNode root, double target) {
        if(root == null) return -1;
        
        double difference = Double.MAX_VALUE;
        int closest = -1;
        while(root != null){
            double c = (double)root.val - target;
            c = Math.abs(c);
            if(difference > c) 
            {
                closest = root.val;
                difference = c;
            }
            
            if((double) root.val > target) root = root.left;
            else root = root.right;
        }
        
        return closest;
    }
    
}