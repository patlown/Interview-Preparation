/*
    Approach: We can iterate through the matrix row by row and for each row
    we can record the largest rectangle, treating the cumulative heights of the columns
    seen so far as a histogram.  The histogram will be built traversing the rows
    Edge Cases: empty matrix, return 0
    Time: O(n^2) or O(m*n)  we iterate through every element 2x, once to build histogram and
    once to find largest rectangle in that histogram.
    Space: O(m) for the stack could hold at most m elements at once.

    See 84. Largest Rectangle in Histogram for write-up on algorithm

*/

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        
        int[] heights = new int[matrix[0].length];
        int max = 0;
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                if(matrix[i][j] == '0'){
                    heights[j] = 0;
                }else{
                    heights[j]++;
                }
            }
            max = Math.max(largestRectangle(heights),max);
        }
        return max;
    }
    private int largestRectangle(int[] heights){
        
        Stack<Integer> stack = new Stack();
        int area = 0, maxArea = 0;
        int i;
        for(i = 0; i < heights.length;){
            if(stack.empty() || heights[stack.peek()] <= heights[i])
                stack.push(i++);
            else{
                int top = stack.pop();
                if(stack.empty()) area = heights[top] * i;
                else area = heights[top] * (i - stack.peek() - 1);
                maxArea = Math.max(area,maxArea);
            }
        }
        
        while(!stack.empty()){
            int top = stack.pop();
            if(stack.empty()) area = heights[top] * i;
            else area = heights[top] * (i - stack.peek() - 1);
            maxArea = Math.max(area,maxArea);
        }
        return maxArea;
        
        
    }
}