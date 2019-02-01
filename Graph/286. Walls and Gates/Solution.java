/*
    First thought: straightforward BFS approach.  Use bfs because it gaurentees shortest path in
    an unweighted graph.  Implementation: Iterate through the matrix adding all
    zeroes to the queue.  After the initial step, the queue should look something
    like [0,0,0,...,0].  Then while the queue is not empty, we can poll nodes from it
    and check their neighbors.  If their neighbors are empty rooms, we can set them to be
    1 + the element we are currently looking at, then add it to the queue.  Doing it this way will
    ensure that we do all lengths of d before doing lengths of d+1.  The queue will go 
    from [0,0,...,0] -> [0,0,1,1,...,1] -> [1,1,...,2] -> ... -> [] This way, the shortest positions
    are considered first.  
*/
class Solution {
    private static final int EMPTY = Integer.MAX_VALUE;
    private static final int GATE = 0;
    private static final List<int[]> DIRECTIONS = Arrays.asList(
        new int[] { 1,  0},
        new int[] {-1,  0},
        new int[] { 0,  1},
        new int[] { 0, -1}
    );
    public void wallsAndGates(int[][] rooms) {
        int m = rooms.length;
        if (m == 0) return;
        int n = rooms[0].length;
        Queue<int[]> q = new LinkedList<>();
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (rooms[row][col] == GATE) {
                    q.add(new int[] { row, col });
                }
            }
        }
        while (!q.isEmpty()) {
            int[] point = q.poll();
            int row = point[0];
            int col = point[1];
            for (int[] direction : DIRECTIONS) {
                int r = row + direction[0];
                int c = col + direction[1];
                if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
                    continue;
                }
                rooms[r][c] = rooms[row][col] + 1;
                q.add(new int[] { r, c });
            }
        }
    }
}

/*
    Recursive approach: Do a DFS solution but we don't need a visited list.  We can add an
    exit condition to our dfs such that if we encounter a count that is lower than the count
    we would set, we can just return.  This way, we dfs from every 0 and the
    lowest possible length is set for each zero and it is not overwritten.
*/
class Solution {
    public void wallsAndGates(int[][] rooms) {
        
        for(int i=0; i<rooms.length; i++) {
            for(int j=0; j<rooms[0].length; j++) {
                if(rooms[i][j] == 0) {
                    helper(rooms, i, j, 0);
                }
            }
        }
    }
    private void helper(int[][] rooms, int i, int j, int count) {
        
        if(i<0 || i>rooms.length-1 || j<0 || j>rooms[0].length-1 || rooms[i][j] < count) {
            return;
        }
        rooms[i][j] = count;
        helper(rooms, i+1, j, count+1);
        helper(rooms, i-1, j, count+1);
        helper(rooms, i, j+1, count+1);
        helper(rooms, i, j-1, count+1);
    }
}