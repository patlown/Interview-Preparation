
// given the example dungeon, lets label cells as follows: 
// +-+-+-+
// |1|2|3|
// +-+-+-+
// |4|5|6|
// +-+-+-+
// |7|8|9|
// +-+-+-+

The dungeon:                              Initial HP knight needed:       
+-------+-------+-------+                 +-------+-------+-------+
|       |       |       |                 |       |       |       |
|  -2   |  -3   |   3   |                 |   7   |   5   |   2   |
|       |       |       |                 |       |       |       |
+-------+-------+-------+                 +-------+-------+-------+
|       |       |       |                 |       |       |       |
|  -5   |  -10  |   1   |                 |   6   |   11  |   5   |
|       |       |       |                 |       |       |       |
+-------+-------+-------+                 +-------+-------+-------+
|       |       |       |                 |       |       |       |
|  10   |  30   |  -5(P)|                 |   1   |   1   |   6   |
|       |       |       |                 |       |       |       |
+-------+-------+-------+                 +-------+-------+-------+

Knight HP: 

// To solve this problem, we can start with the simpliest cases.
// Les's say,

// If the knight starts from cell 9.
Initial HP: 6 (6 - 5 = 1), which means as long as the knight has 6 HP when reaching cell 9, 
he would be fine.

// If the knight starts from cell 6.
Initial HP: 5 (5 + 1 = 6), which means as long as the knight has 5 HP when reaching cell 6, 
he would be fine.

// If the knight starts from cell 8.
Initial HP: 1 (1 + 30 >= 6, HP needs to be at least 1, 0 means the knight is already dead), 
which means as long as the kinght has 1 HP when reaching cell 7, he would be fine.

// If the knight starts from cell 5.
Emm... the knight now has two options, going right or going down.
If go right (5 --> 6 --> 9), Initial HP(R): 15 (15 - 10 = 5)
If go down (5 --> 8 --> 9), Initial HP(D): 11 (11 - 10 = 1)
Hence, Initial HP = MIN(HP(R), HP(D)) = 11

// Sub-problems and state: 
Let dp[i][j] denote Initial HP needed if the knight starts from dungeon[i][j].

// recurrence relation:
dp[i][j] = min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
if(dp[i][j] <= 0) dp[i][j] = 1;

class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        if(dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 1;
        int n = dungeon.length;
        int m = dungeon[0].length;
        int[][] dp = new int[n][m];
        dp[n-1][m-1] = 1 - dungeon[n-1][m-1];
        dp[n-1][m-1] = dp[n-1][m-1] <= 0 ? 1 : dp[n-1][m-1];
        
        for(int i = n-1; i >= 0; i--){
            for(int j = m-1; j >= 0; j--){
                if(i == n-1 && j == m-1) continue;
                int hpDown = i+1 == n ? Integer.MAX_VALUE : dp[i+1][j] - dungeon[i][j];
                int hpRight = j+1 == m ? Integer.MAX_VALUE : dp[i][j+1] - dungeon[i][j];
                int hp = Math.min(hpDown,hpRight);
                dp[i][j] = hp <= 0 ? 1 : hp;
            }
        }
        return dp[0][0];
    }
}
/*
    Can also do the same solution with no extra space by changing the input matrix
*/
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        if(dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 1;
        int n = dungeon.length;
        int m = dungeon[0].length;
        
        dungeon[n-1][m-1] = dungeon[n-1][m-1] < 0 ? 1 + Math.abs(dungeon[n-1][m-1]) : 1;
        
        for(int i = n-1; i >= 0; i--){
            for(int j = m-1; j >= 0; j--){
                if(i == n-1 && j == m-1) continue;
                int hpDown = i+1 == n ? Integer.MAX_VALUE : dungeon[i+1][j] - dungeon[i][j];
                int hpRight = j+1 == m ? Integer.MAX_VALUE : dungeon[i][j+1] - dungeon[i][j];
                int hp = Math.min(hpDown,hpRight);
                dungeon[i][j] = hp <= 0 ? 1 : hp;
            }
        }
        return dungeon[0][0];
    }
}