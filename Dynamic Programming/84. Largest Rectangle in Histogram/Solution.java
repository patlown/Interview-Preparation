/*
    Brute Force: For each index, find the largest rectangle from it to the
    end of the array.  For each index, the largest rectangle will be the 
    max(smallest value * length, tallest value).
    time: O(n^2), space: O(1)

    Stack Based Approach: Intuition: We want to consider all possible max size
    rectangles.  We can solve it with the following approach:
        1. Add index to stack if current value is equal or bigger than top of stack
        2. Otherwise, keep removing from stack until a number which is smallaer or
            equal than current is found.
        3. Calculate area every time you remove where area is :
            if(stack is empty)
                area = input[top] * index
            else
                area = input[top] * (index - stack.peek() - 1)
            Why is this the area?  If our stack is empty, we know that we have
            removed the smallest value that we have seen which means we can simply
            calculate the size of the rectangle as the current index * the smallest value.
            If our stack is not empty, we must have values smaller than or equal to
            the index we are currently considering.  Since the current index is smaller than
            our current top, we can consider the rectangle from the next smallest value in the stack
            exclusive to the current index exclusive.  This will return a rectangle with
            height of current top and width of itself plus all elements on its right (elements with
            larger or equal heights) that are left of the current index.  

        Implementation: We need to make sure our stack is empty after our main loop.
        For example: if the end of the array is all increasing, we will never calculate areas
        for them and the stack will have elements in it.  To consider these, we just repeat
        the area calculation process for the remaining elements in the stack.
*/

class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack();
        int maxArea = 0;
        int area = 0;
        int i;
        for(i = 0; i < heights.length;){
            if(stack.empty() || heights[stack.peek()] <= heights[i]){
                stack.push(i++);
            }else{
                int top = stack.pop();
                if(stack.empty()) area = heights[top] * i;
                else area = heights[top] * (i - stack.peek() -1);
                maxArea = Math.max(area,maxArea);
            }
        }
        
        while(!stack.empty()){
            int top = stack.pop();
            if(stack.empty()) area = heights[top] *i;
            else area = heights[top]* (i-stack.peek()-1);
            maxArea = Math.max(area,maxArea);
        }
        return maxArea;
    }
}
/*
    Notice that the rectangle of largest area will be the maximum of:
        1. Widest possible rectangle with height of shortest bar.
        2. Largest rectangle confined to left of shortest bar (sub problem)
        3. Largest rectangle confined to right of shortest bar (sub problem)

    Average Case: We are doing n work then splitting the array in two and repeating.  So our total
    runtime will be O(nlogn). Space is O(n) where recursion does in the worst case
    n steps.  
    Worst Case: We have a sorted array, so the split will do one element at a time
    and we will end up with an O(n^2) solution.  
*/
class Solution {
    public int largestRectangleArea(int[] heights) {
        if(heights == null || heights.length == 0) return 0;
        return largestRectangleArea(heights,0,heights.length);
    }
    private int largestRectangleArea(int[] heights, int start, int end){
        if(start == end) return 0;
        if(end - start == 1) return heights[start];
        int shortest = start;
        for(int i = start; i < end; i++){
            if(heights[i] < heights[shortest]) shortest = i;
        }
        int middle = heights[shortest] * (end-start);
        int left = largestRectangleArea(heights,start,shortest);
        int right = largestRectangleArea(heights,shortest+1,heights.length);
        
        return Math.max(middle,Math.max(left,right));
        
    }
}