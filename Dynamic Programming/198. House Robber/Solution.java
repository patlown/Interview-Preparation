/*
    Let nums[i] hold the max amount that can be stolen from non=adjacent houses 
    from i-n.  The result will then be the max of (nums[0],nums[1]) as nothing
    could be gained by not including either of those in the final result.  Our 
    optimal formula is then nums[i] = max(nums[i+2],nums[i+3]) as both those indexes
    are non-adjacent to i.  We don't need to consider i+4...n in teh formula as
    i+4 will have the potential to be included if we chose nums[i+2] since they are adjacent
    as well as i+5 adjacent to i+3.  Thus, there is no reason to not choose i+2/i+3
    as it does not stop us from considering others later on.  This will allow us to check
    all possibilities.
    Time: O(n)
    Space:O(1)
*/
class Solution {
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        
        for(int i = nums.length-3; i >= 0; i--){
            nums[i] += Math.max(nums[i+2],i+3 == nums.length ? 0 : nums[i+3]);
        }
        return Math.max(nums[0],nums[1]);
    }
}