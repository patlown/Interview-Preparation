/*
    First approach: do a BFS from every zero we encounter.  We need to fill
    our queue keeping trrack of it we've seen a border.  If go through the whole
    queue without seeing a border, poll all positions forom queue and set them 
    'X'.
*/
class Solution {
    public void solve(char[][] board) {
        if(board == null || board.length == 0) return;
        Set<Integer> visited = new HashSet();
        
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == 'O' && !visited.contains(i*10000+j))
                    bfs(board,i,j,visited);
            }
        }
    }
    int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
    private void bfs(char[][] board, int i, int j, Set<Integer> visited){
        
        Queue<int[]> q = new LinkedList();
        q.add(new int[] {i,j});
        boolean isBorder = isBorder(board,i,j);
        Queue<int[]> flip = new LinkedList();
        flip.add(new int[] {i,j});
        visited.add(i*10000+j);
        
        while(!q.isEmpty()){
            int[] curr = q.poll();
            for(int[] dir : dirs){
                int x = curr[0] + dir[0];
                int y = curr[1] + dir[1];
                if(isValid(board,x,y,visited)){
                    if(board[x][y] == 'O'){
                        if(isBorder == false)
                            isBorder = isBorder(board,x,y);
                        q.add(new int[] {x,y});
                        flip.add(new int[] {x,y});
                        visited.add(x*10000+y);
                    }
                }
            }  
        }
        
        if(!isBorder){
            while(!flip.isEmpty()){
                int[] change = flip.poll();
                board[change[0]][change[1]] = 'X';
            }
        }
        return;
    }
    
    private boolean isBorder(char[][] board,int x, int y){
        return x == 0 || y == 0 || x == board.length-1 || y == board[0].length-1;
    }
    private boolean isValid(char[][] board, int x, int y, Set<Integer> visited){
        return x >= 0 && x < board.length && y >= 0 && y < board[x].length && !visited.contains(x*10000+y);
    }   
}
/*
    Can also do a DFS from any border 'O' and keep track of all positions visited.
    After first iteration, do another pass and flip all Os that weren't visited.
*/
