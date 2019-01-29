/*
Compute thje largest value to the right of every index.  Use this to iterate
through the prices array and determine the best time.
O(n) time, O(n) space
*/
class Solution {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) return 0;
        int[] right = new int[prices.length];
        right[prices.length-1] = prices[prices.length-1];
        for(int i = prices.length-2; i >= 0; i--){
            if(prices[i] > right[i+1]) right[i] = prices[i];
            else right[i] = right[i+1];
        }
        
        int max = 0;
        for(int i = 0; i < prices.length; i++){
            if(right[i]-prices[i] > max) max = right[i]-prices[i];
        }
        return max;
    }
}

/*
    Two pointer solution: start left and right pointer at the end of array,
    while right > left, move left and consider the difference as a new potential
    max.  If we a find a left value larger than our current right, move left and
    right to that element and repeat.  this works as we only move left as long
    as left is smaller than right, once we find a larger left, we move both to that
    element.  We run no risk of not considering the elements between right and left
    because any solution would be larger when considering the new left than any in between
    as they are smaller.
*/
class Solution {
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) return 0;
        int left = prices.length-1, right = prices.length-1;
        int max = 0;
        while(left >= 0){
            if(prices[left] <= prices[right]){
                max = Math.max(max,prices[right]-prices[left]);
                left--;
            }else{
                right = left;
            }
        }
        
        return max;
    }
}