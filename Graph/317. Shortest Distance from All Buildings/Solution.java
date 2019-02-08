/*
The algorithm is broken down into 3 steps:

1.Do a pass through the grid changing all 1s and 2s to negative 1s and 2s.  Add all 1s to a buildings queue
2.BFS from each building in the queue
	-when bfsing, consider all four directions from each position, if it valid ie. is not -1,-2 or already visited, change its distance to 
	be 1 + the current position or +1 if the current position is a building
	-also keep track of how many buildings this position has seen, if the position doesn't see all buildings, we can't consider it
3.iterate through the grid looking for the lowest value that is positive and has seen all buildings.
*/

class Solution {
    public int shortestDistance(int[][] grid) {
        
        int numBuildings = 0;
        Queue<int[]> buildings = new LinkedList();
        for(int i =0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == 1){
                    grid[i][j] = -1;
                    buildings.add(new int[] {i,j});
                    numBuildings++;
                }
                if(grid[i][j] == 2) grid[i][j] = -2;
            }
        }
        Map<Integer,Integer> buildingsSeen = new HashMap();
        while(!buildings.isEmpty()){
            bfs(buildings.poll(),grid,buildingsSeen);
        }
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] > 0 && buildingsSeen.get(i*10000+j) == numBuildings){
                    min = Math.min(min,grid[i][j]);
                }
            }
        }
        
        return min == Integer.MAX_VALUE ? -1 : min;
    }
    int[][] dirs = {{1,0},{0,1},{-1,0},{0,-1}};
    private void bfs(int[] pos, int[][] grid, Map<Integer,Integer> buildingsSeen){
        
        Queue<int[]> q = new LinkedList();
        q.add(pos);
        Set<Integer> visited = new HashSet();
        visited.add(pos[0] * 10000 + pos[1]);
        
        while(!q.isEmpty()){
            int[] curr = q.poll();
            int i = curr[0];
            int j = curr[1];
            for(int[] dir : dirs){
                int nexti = i + dir[0];
                int nextj = j + dir[1];
                if(isValid(nexti,nextj,grid) && !visited.contains(nexti*10000+nextj)){
                    if(grid[i][j] == -1) grid[nexti][nextj] += 1;
                    else grid[nexti][nextj] += grid[i][j] + 1;
                    q.add(new int[] {nexti,nextj});
                    visited.add(nexti*10000+nextj);
                    buildingsSeen.put(nexti*10000+nextj,buildingsSeen.getOrDefault(nexti*10000+nextj,0)+1);
                }
            }
        }
        
    }
    
    private boolean isValid(int i, int j, int[][] grid){
        return i >= 0 && i < grid.length && j >= 0 && j < grid[i].length && grid[i][j] != -1 && grid[i][j] != -2;
    }
    
}