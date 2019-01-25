/*
    First thought: iterate through string, if we find a matching word, repeat on the
    substring from after this word to the end of the original string. if we run out of string
    with no match, return false, if we get a match and call function w/ empty string, return true

    we can improve the efficieny of this algorithm by keeping a memo array that keeps track of
    whether the ith position in the string has a matching segmentation to the words in the dict.
    This way, if we come across similar sub-problems, we can just return our pre-computed value
    rather than recomputing it.
    O(n^2) time and O(n) space for call stack and memo
*/

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet();
        for(String string : wordDict) dict.add(string);
        Boolean[] memo = new Boolean[s.length()];
        
        return helper(s,dict,0,memo);
    }
    
    private boolean helper(String s,Set<String> dict, int start,Boolean[] memo){
        if(start == s.length()) return true;
        if(memo[start] != null) return memo[start];
        
        for(int i = start; i < s.length(); i++){
            if(dict.contains(s.substring(start,i+1))){
                memo[start] = helper(s,dict,i+1,memo);
                if(memo[start])return true;
            }
        }
        memo[start] = false;
        return memo[start];
    }
}


/*
Second thought: We can do a BFS, keep a queue of integers that we will
add to the q when we find a match.  Initialize the q with a 0 for the start of the string, then
poll this from the q and iterate from its position to the end of the string.  If we find a matching
word, add the end of the word's index to the q.  If we add the end of string index to the queue, we know
that we have found a match and can return true.  Make sure to keep a visited list or we will traverse
the same parts of the string multiple times and add many duplicates to our q
O(n^2) time, O(n) space for q
*/
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet();
        for(String str : wordDict)
            dict.add(str);
        
        Queue<Integer> q = new LinkedList();
        boolean[] visited = new boolean[s.length()];
        q.add(0);
        while(!q.isEmpty()){
            int start = q.poll();
            if(visited[start]) continue;
            for(int i = start; i < s.length(); i++){
                if(dict.contains(s.substring(start,i+1))){
                    if(i+1 == s.length()) return true;
                    q.add(i+1);
                }
            }
            visited[start] = true;
        }
        return false;
    }
}

/*
Dynamic programming approach: keep a table from 0 to n where n is the string length.  dp[i] indicates
if the substring from 0 to i-1 can be segmented.  Return dp[n].  We can build our table from left to right
by checking every segmentation for each of our n indexes.  This can be achieved with a double for loop.
*/
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        Set<String> dict = new HashSet();
        for(String str : wordDict) dict.add(str);
        
        for(int i = 1; i <= s.length(); i++){
            for(int j = 0; j < i; j++){
                if(dp[j] && dict.contains(s.substring(j,i))){
                    dp[i] = true;
                }
            }
        }
        
        return dp[s.length()];
    }
}