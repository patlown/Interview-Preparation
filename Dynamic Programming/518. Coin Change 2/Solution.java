/*
    Recursive approach: how many ways are there to make change if we consider
    using the mth ocin at least once.

    Substructure: To count total number of solutions, we can divie all solutions
    into two sets:
        1. Solutions that do not contain mth coin
        2. Solutions that contain at least one mth(Sm) coin.
        Let count(coins[],#coins,amount) = 
                count(coins,#coins-1,amount) + count(coins[],#coins,amount-Sm)
    DP Solution:  Notice that we can consider our subproblems in a table as such:
        amount = 5
        coins = {1,2,5}
    
    Amounts-> 0   1   2   3   4   5
    Coins   +--+--+--+--+--+--+--+--+
    []      | 1 | 0 | 0 | 0 | 0 | 0 |
            +--+--+--+--+--+--+--+--+
    [1]     | 1 | 1 | 1 | 1 | 1 | 1 |
            +--+--+--+--+--+--+--+--+
    [1,2]   | 1 | 1 | 2 | 2 | 3 | 3 |
            +--+--+--+--+--+--+--+--+
    [1,2,5] | 1 | 1 | 2 | 2 | 3 | 4 |
            +--+--+--+--+--+--+--+--+

    Table works as follow:
        If zero amount, only one way to make change
        If no coins, zero ways to make change (except for zero amount)
        When considering a coin, the unique ways to make change is:
            table[coin][amount] = table[coin][amount-coin] + table[coin-1][amount]
        
    **Notice we only update 1 row at a time from left to right.
        We can reduce space usage by using only a 1D array and calculating the rows
        one by one.  The table is of size amount+1 and works as such:

    +---+---+---+---+---+---+
    | 1 | 0 | 0 | 0 | 0 | 0 |  ---->   Calculate all ways for coin 1
    +---+---+---+---+---+---+
                                    +---+---+---+---+---+---+
                                    | 1 | 1 | 1 | 1 | 1 | 1 |  ---->
                                    +---+---+---+---+---+---+
    Calculate all ways for coin 2
    +---+---+---+---+---+---+
    | 1 | 1 | 2 | 2 | 3 | 3 |  ---->    
    +---+---+---+---+---+---+             Calculate all ways for coin 3
                                     +---+---+---+---+---+---+
                                    | 1 | 1 | 2 | 2 | 3 | 4 |
                                    +---+---+---+---+---+---+                  

    The table works by: 
        for each coin,
            start at its index in the table
            iterate through from the start to the end
                at each index, the unique ways to make that amount is
                    table[index] += table[index-coin]
    This algorithm builds the table up considering one coin at a time and calculating all the unique
    ways that coin can build every amount from itself to the total.

    This is O(n^2) and O(n) space.

*/
class Solution {
    public int change(int amount, int[] coins) {
        return count(coins,coins.length,amount);
    }
    private int count(int[] coins, int m, int amount){
             // table[i] will be storing the number of solutions for 
        // value i. We need n+1 rows as the table is constructed 
        // in bottom up manner using the base case (n = 0) 
        int[] table=new int[amount+1]; 

        // Base case (If given value is 0) 
        table[0] = 1; 

        // Pick all coins one by one and update the table[] values 
        // after the index greater than or equal to the value of the 
        // picked coin 
        for(int i=0; i<m; i++) 
            for(int j=coins[i]; j<=amount; j++) 
                table[j] += table[j-coins[i]]; 

        return table[amount]; 
    }
}