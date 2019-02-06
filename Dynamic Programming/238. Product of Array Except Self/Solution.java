/* Approach: Keep track of a prefix where prefix is the product of all elements before the 
current index. (this can be kept track of during iteration). Also, we need a suffix array which is 
as such: suffix[i] = product from suffix[i+1]-suffix[n]. 
Once we have these, the product except self for every element is 
simply prefix * suffix[i]. To use no extra space, we can overwrite our 
suffix array as once we have used suffix[i], we will not use it again.

 Edge Cases to Consider: Zeroes in the array? empty array? negatives?
*/
class Solution {
    public int[] productExceptSelf(int[] nums) {
        if(nums == null || nums.length == 0) return nums;
        
        int prefix = 1;
        int[] suffix = new int[nums.length];
        suffix[suffix.length-1] = 1;
        for(int i = suffix.length - 2; i >= 0; i--){
            suffix[i] = nums[i+1] * suffix[i+1];
        }
        
        for(int i = 0; i < nums.length; i++){
            int temp = prefix * nums[i];
            suffix[i] = prefix * suffix[i];
            prefix = temp;
        }
        
        return suffix;
        
    }
}