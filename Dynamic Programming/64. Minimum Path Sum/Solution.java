/*
    Simple Recursive APproach: keep sum from top left to bottom right, returning
    the min sum.  Can cache these results which gives a nice memoized approach.  THe space
    is another O(n) to store another grid plus the recursive call stack.  We must
    traverse every position in the grid just once so our solution is just O(n) time.  If we had
    not cached our answers, we would be making O(2^n) calls as every position
    will call two more positions.
*/
class Solution {
    int[][] memo;
    public int minPathSum(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        memo = new int[grid.length][grid[0].length];
        return minPath(grid,0,0,memo);
    }
    private int minPath(int[][] grid,int i, int j, int[][] memo){
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[i].length) return Integer.MAX_VALUE;
        if(i == grid.length-1 && j == grid[i].length-1) return grid[i][j];
        if(memo[i][j] != 0) return memo[i][j];
        
        int a = minPath(grid,i+1,j,memo);
        int b = minPath(grid,i,j+1,memo);
        memo[i][j] = grid[i][j] + Math.min(a,b);
        return memo[i][j];
        
    }
}
/*
    A simple O(n) time and O(1) space solution using dynamic programming.  From our
    recursive formula, we know that each position's sum depends on the sum of itself
    plus the min of the two positions it can move to.  Using this, we can fill our grid
    up from right to left bottom to top making sure we have computed the dependencies
    of each position before we calculate the position. 

    Implementation: start by computing the final row and final columns, these are easy
    as they can only move one direction so their sums are themselves plus their next element.
    We can then calculate the rest of the grid by computing the sums of each positions
    by adding themselves with the min of their dependencies.
*/
class Solution {
    public int minPathSum(int[][] grid) {
        int lastRow = grid.length-1;
        int lastCol = grid[lastRow].length-1;
        //compute the outside right and bottom of matrix
        for(int i = lastRow-1; i >= 0; i--){
            grid[i][lastCol] += grid[i+1][lastCol];
        }
        for(int i = lastCol-1; i >= 0; i--){
            grid[lastRow][i] += grid[lastRow][i+1];
        }
        //compute values of inner rectangle
        lastCol--; lastRow--;
        for(int i = lastRow; i >= 0; i--){
            for(int j = lastCol; j >= 0; j--){
                grid[i][j] += Math.min(grid[i+1][j],grid[i][j+1]);
            }
        }
        return grid[0][0];
        
    }
}