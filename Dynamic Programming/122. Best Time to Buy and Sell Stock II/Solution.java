/*
Do a DFS like traversal from every index each day that we can seel a stock
for profit.  We can then prune our dfs tree by caching values so we only do
each computation once.  This got a TLE but passed 200/201 cases so it correctly
solves the problem.  just not efficiently.
O(n^2) time , O(n) space 
*/
class Solution {
    public int maxProfit(int[] prices) {
        int max = 0;
        Integer[] memo = new Integer[prices.length+1];
        memo[prices.length] = 0;
        for(int i =0; i < prices.length; i++){
            max = Math.max(max,dfs(prices,i,memo));
        }
        return max;
    }
    
    private int dfs(int[] prices, int start,Integer[] memo){
        if(memo[start] != null) return memo[start];
        int max = 0;
        for(int i = start; i < prices.length; i++){
            for(int j = i+1; j < prices.length; j++){
                if(prices[j] > prices[i]){
                    max = Math.max(max,prices[j]-prices[i] + dfs(prices,j+1,memo));
                }
            }
        }
        memo[start] = max;
        return memo[start];
    }
}

/*
One Pass Peak and Valley Approach:  We want to match peaks with their nearest
valley.  Like the previous stock problem, we use two poihnters and iterate the
left as long as we are moving down from a peak, before we leave a valley, add it
to the total profit.  
O(n) time, O(1) space.
*/
class Solution {
    public int maxProfit(int[] prices) {
        int left = prices.length-2, right = prices.length-1;
        int total = 0;
        while(left >= 0){
            if(prices[left] < prices[right]){
                if(left == 0 || prices[left-1] > prices[left]){
                    total += prices[right] - prices[left];
                    left--;
                    right = left;
                    left--;
                }
                else{
                    left--;
                }
            }else{
                right = left;
                left--;
            }
        }
        return total;
    }
}