/*
    This question is pretty straightforward if you can use O(n) space or 
    O(n^2) time or modify the input array.  First thoughts were to use a 
    set to save seen values and return any that show up twice.  Second thought
    was to sort and compare adjacent values to see if they are equal and third
    thought was do a scan of the other elements for each element in the array.

    To fit the constraints, we must approach this problem in a different way:
    Since our numbers are 1-n inclusive and we have an n+1 size array, all numbers
    in our values in our array will point to valid  elements.  We will be able to 
    traverse our array using the values of the indices.  We can infer that 
    we will get stuck in a cycle traversing this way as there are atleast two indices
    that will point to the same  

*/


class Solution {
    public int findDuplicate(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        
        
        int slow = nums[0];
        int fast = nums[0];
        
        do{
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while(slow != fast);
        
        int step = nums[0];
        while(step != slow){
            step = nums[step];
            slow = nums[slow];
        }
        
        return slow;
        
        
    }
}
