/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 *//**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */

 /*
    At first glance, this seems like a straightforward DFS problem.  However, it has a caveat that
    the robot is an actual object so you can't simply rely on the call stack to return you to your
    previous state.  You must actually move the robot back to its original position.  In addition,
    we are flying blind so we must build a relative graph that we consult throughout our dfs.

    This can be achieved cleverly by keeping an array of directions.  Building a relative graph where our start
    position is (0,0) and doing a dfs search in each direction from our starting point. Once we are in our
    dfs function, we can check if we've seen the spot before or if it is valid.  If we pass that condtion
    we start a dfs in each direction from the current position.  However, this is the clever part, we keep track
    of the last direction that we used and start iterating from the new spot in the direction after the previous.  
    The direction array is built as {{0,1},{1,0}, {0, -1}, {-1, 0}} such that each direction's opposite is
    two spots away.  This is conveniently matched with how we iterate through the directions.  Exactly two iterations
    into the direction array, our robot will be pointed back at the node it came from and be considering the opposite
    direction of the direction it took to get to its current spot.  This will keep the robot oriented correctly within
    the relative graph.

    At the end of the dfs main loop, the robot will be facing the exact way it came in.  At this point
    we need to revert it back to its previous state by turning around, travelling back and then turning
    back around.  This way, the initial call will be able to operate as if the prior call never happened.

 */
class Solution {
    
    private final int[][] DIRS = new int[][] {{0, 1}, {1,0}, {0, -1}, {-1, 0}};

   public void cleanRoom(Robot robot) {
       Set<Integer> visitedCells = new HashSet<>();
       visitedCells.add(0); // keeps positions relative to start 
       robot.clean();
       for(int i = 0; i < 4 ; i++) {
           robot.turnRight();
           dfs(visitedCells, DIRS[i][0], DIRS[i][1], i, robot);
       }
   }

   private void dfs(Set<Integer> visited, int x, int y, int i, Robot robot) {
       int cellId = x * 100000 + y; // could be any number bigger than max room length
       if(visited.contains(cellId) || !robot.move()) {
           return;
       }
       visited.add(cellId);
       robot.clean();
       /*
       try every direction starting with the one after the one we just came from
       this will keep everything on track as once the robot has turned around, it will be considering
       the exact opposite of the position it came from.
       */
       for(int j = 0; j < 4; j++) {
           int nextMove = (i + 1 + j) % 4; // we compute next position to be next after previous rotation
           int newX = x + DIRS[nextMove][0]; 
           int newY = y + DIRS[nextMove][1];
           robot.turnRight();
           dfs(visited, newX, newY, nextMove, robot);
       }
       //go back
       robot.turnRight();
       robot.turnRight();
       robot.move();
       //set to the position before the call
       robot.turnRight();
       robot.turnRight();
   }
}