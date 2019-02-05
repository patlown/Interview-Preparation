/*
    DP Approach: Notice taht this essentially the same problem as House Robber 1
    with 1 added constraint.  If we rob the first house, we can't rob the last hosue. If
    we rob the last house we can't rob the first.  Our approach is to do the same as house robber
    but do it twice.  One iteration will do consider the max when exclude house 0 and
    the other will consider the max when we exclude house 1.  We can then take the 
    max of these and return it as the max total.
    Time: O(n) two passes over the input array
    Space: O(n), we make a copy of the input array so we can have two separate copies
        for the iteration.
*/
class Solution {
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        
        int[] copy = nums.clone();
        int n = nums.length;
        int first = rob(nums,0,n-2);
        int last = rob(copy,1,n-1);
        return Math.max(first,last);
    }
    
    private int rob(int[] nums,int lo, int hi) {
        int size = hi-lo+1;
        if(size == 1) return nums[lo];
        if(size == 2) return Math.max(nums[lo],nums[hi]);
        for(int i = hi-2; i >= 0; i--){
            nums[i] += Math.max(nums[i+2],i+3 > hi ? 0 : nums[i+3]);
        }
        return Math.max(nums[lo],nums[lo+1]);
    }
}