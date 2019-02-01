/*
    First approach: recursive using string indices.  Start at beginning of string.  Work
    through string moving down paths with valid 1 or 2 digit numbvers, return
    1 when reching end of string.  Be careful of zeroes.  If we come across a zero
    we can just return 0 as no string can be decoded using a single zero value.
    If we have enough string left to consider two digit numbers then check if the two
    digit is valid, if it is, the current index in string is equal to i+1 + i+2 indices
    in the string.  If not valid, this string is just equal to i+1 index.

    Runtime: we can end up traversing the rest of the string for every index that
    we travel down and can potentially do this for every index so the running time
    is O(n^2) with O(n) space as each call stack will recurse to the end of the string
*/

/*
    We can easily notice that we may be doing the same computations and going down
    the same call stacks multiple times.  We can cache values at the indices of the string
    so that we don't use any extra computation time than what is necessary.
    This solution is O(n) time, O(n) space for each string index.
*/

/*
    We can see that the number of decodings at any index in the string is
    just the number of decodings in the i+1 index + the i+2 index if valid. Thus,
    we can do a dp approach from right to left, where an empty string has 1 decoding
    a single digit has 1 decoding if it isn't a zero and all other decodings
    can be determined using the rules in the first sentence.
    O(n) time, O(n) space
*/
class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) return 0;
        int[] dp = new int[s.length()+1];
        dp[s.length()] = 1;
        if(Integer.parseInt(s.substring(s.length()-1,s.length())) != 0)
            dp[s.length()-1] = 1;
        else
            dp[s.length()-1] = 0;
        
        for(int i = s.length()-2; i >= 0; i--){
            if(s.charAt(i) == '0') continue;
            int check = Integer.parseInt(s.substring(i,i+2));
            if(check > 0 && check < 27){
                dp[i] = dp[i+1] + dp[i+2];
            }else{
                dp[i] = dp[i+1];
            }
        }
        
        return dp[0];
    }
}

/*
    We can then further notice that we only need two variables to keep iterating
    and solving further.  Thus, we will only keep a constant size array of length
    2.  We will then calculate our answers based of these values, then shift our
    current array elements to the right and insert the new value in the front. This
    is like a queue.
    O(n) time, O(1) space
*/
class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() == 0) return 0;
        int[] dp = new int[2];
        dp[1] = 1;
        
        if(Integer.parseInt(s.substring(s.length()-1,s.length())) != 0) dp[0] = 1;
        else dp[0] = 0;
        
        int temp;
        for(int i = s.length()-2; i >= 0; i--){
            
            int check = Integer.parseInt(s.substring(i,i+2));
            
            if(s.charAt(i) == '0') temp = 0;
            else if(check > 0 && check < 27) temp = dp[0]+dp[1];
            else temp = dp[0];
            
            dp[1]=dp[0];
            dp[0] = temp;
        }
        
        return dp[0];
    }
}
