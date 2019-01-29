/*
First thought: recoursive approach with memoization: every one is built
off the previous 2, like fibonacci numbers.  We can cache these values
so not to do computations and call stack traversals more than once.
*/
class Solution {
    public int climbStairs(int n) {
        if(n < 0) return 0;
        if(n <= 1) return 1;
        
        int[] memo = new int[n+1];
        memo[0] = 1;
        memo[1] = 1;
        climbStairs(n,memo);
        return memo[n];
    }
    private int climbStairs(int n, int[] memo){
        if(memo[n] != 0) return memo[n];
        memo[n] = climbStairs(n-1,memo) + climbStairs(n-2,memo);
        return memo[n];
    }
}
/*
    Build our result one step at a time using a result cache. Base cases of 0
    or 1 step left are just one so we can store those first.  Start from 2 and
    build cache by adding previous two elements.
*/
class Solution {
    public int climbStairs(int n) {
        int[] cache = new int[n+1];
        cache[0] = 1; cache[1] = 1;
        for(int i = 2; i < cache.length; i++){
            cache[i] = cache[i-1] + cache[i-2];
        }
        return cache[n];
    }
}