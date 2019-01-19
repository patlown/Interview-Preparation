
/*
    Idea: hanging 1,2,...,n is essentially choosing an element i (1-n) to hang
    the tree from and then hangin 1...i-1 on its left and i+1..n on its right. 
    1..i-1 and i+1...n can simply be thought of as a smaller problem with
    1..n.  Thus, the only thing that matters about these subtrees is their size
    and not the specific numbers.  Using this idea, we can only have so many
    combinations.  Any 1..i-1 and i+1...n will be of some size <= n so we can keep
    an array of precomputed values of size n+1.  Having 0 or 1 elements in a 
    subtree will give 1 possibility.  The rest can be calculated in an O(n^2) time
    by using a nested for loop to populate our dp array.  Our j loop iterates
    through every way to split an i sized tree.  We take the product of each side
    as we are counting all possibilites on left with all possibilites on right.
*/

class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        
        for(int i = 2; i<= n; i++){
            for(int j = 1; j<= i; j++){
                dp[i] += dp[j-1] * dp[i-j];
            }
        }
        return dp[n];
    }
}
/*
    Same idea, just with memoization and recursion instead of dp with 
    iteration.
*/

class Solution {
    public int numTrees(int n) {
        if(n == 0 || n == 1) return 1;
        int sum = 0;
        int[] memo = new int[n+1];
        
        if(memo[n] != 0) return memo[n];
        for(int i = 1; i <= n; i++){
            if(memo[i-1] == 0) memo[i-1] = numTrees(i-1);
            if(memo[n-i] == 0) memo[n-i] = numTrees(n-i);
            sum += memo[i-1] * memo[n-i];
        }
        
        return sum;
    }
}