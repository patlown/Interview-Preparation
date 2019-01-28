/*
    First appraoch: DFS from each reachable city from the first city and search
    for the largest possible amount of vacation days.  Since this problem 
    decomposes to subproblems that depend on previous decisions, we can memoize
    our approach.  When we are checking a current city and  week, we can check
    to see if we have previously computed this before.

    Implementation tips: we need to dfs from each feasible city, in our dfs
    function, check that week is valid and if so, keep a daysHere value that
    counts this current week and city.  Then find the max vacation days possible
    by doing a dfs from all the reachable cities.  Once done, return the daysHere +
    the maxVacation days found.

*/
class Solution {
    private int[][] memo;
    
    public int maxVacationDays(int[][] flights, int[][] days) {
        int ans = Integer.MIN_VALUE;
        memo = new int[flights.length][days[0].length];
        for (int[] row : memo) Arrays.fill(row, -1);

		for (int i=0; i<flights[0].length; i++) {
			// starting at city 0, either stay or fly to a reachable city
            if (i == 0 || flights[0][i] == 1) {
                int maxDays = dfs(flights, days, i, 0);
                ans = Math.max(maxDays, ans);
            }             
        }
        return ans;       
    }
    
    private int dfs(int[][] flights, int[][] days, int currCity, int week) {
        if (week >= days[0].length) return 0;
        // we've computed the max days possible at the current city and week
        if (memo[currCity][week] != -1) return memo[currCity][week];
        
        int daysHere = days[currCity][week];
        int max = Integer.MIN_VALUE;
        
        for (int i=0; i<flights[currCity].length; i++) {
            // either stay or fly to a reachable city
            if (i == currCity || flights[currCity][i] == 1) {
                int maxDays = dfs(flights, days, i, week+1);
                max = Math.max(maxDays, max);
            }
        }
        int total = daysHere + (max == Integer.MIN_VALUE ? 0 : max);
        memo[currCity][week] = total;
        return total;
    }
}
/*
    Can also do a space friendly dp solution by recognizing
    that you need only 2 columns of the dp array ata time.  Build a main
    dp array, in every iteration, build a 1D temp array that calculates new 
    maxes based off current dp array.  At end of that step, set dp to point
    to the temp array.  Repeat until you fill up maxes for first week.  Then,
    to generate answer, check the last dp array for the max and make sure the max
    is reachable from city 0.  Can also do this step during main iteration
    by adding a conditional that checks if the week is 0 and subsequently
    calculates the correct result.
*/
class Solution {
    public int maxVacationDays(int[][] flights, int[][] days) {
        int city = flights.length;
        int week = days[0].length-1;
        
        int[] dp = new int[city];
        
        for(int i = 0; i < dp.length; i++){
            dp[i] = days[i][week];
        }
        
        week--;
        for(;week >= 0; week--){
            int[] temp = new int[dp.length];
            for(int i = 0; i < city; i++){
                int daysHere = days[i][week];
                for(int j = 0; j < dp.length; j++){
                    if(i==j || flights[i][j] == 1){
                        temp[i] = Math.max(temp[i],dp[j]+daysHere);
                    }
                }
            }
            dp = temp;
        }
        int res = 0;
        //must make sure there is flight from first city to max that we will return
        for(int i=0; i < dp.length; i++){
            if(i == 0 || flights[0][i] == 1){
                res = Math.max(res,dp[i]);
            }
        }
        return res;
        
        
    }
}
