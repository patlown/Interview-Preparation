
/*
    Straight forward approach: build a graph and doo topological sort.  If a topological ordering
    exists then we know that the schedule is valid.  Implementation Concerns:  We want an efficient
    graph structure for the commands that we will use.  We need to have an adjacency list for each node that
    represents its outgoing edges, an int to keep track of the number of incoming edges and a course number
    that connects the node to the course it represents.  Three parts: build the graph with a hashmap that has a
    key of the course number and a value of the node (no need to hash nodes). This should take
    O(n^2) time where n is the number of courses.  Then we need to iterate through our map and 
    add the nodes with no incoming edges to our queue.  This step takes O(n).  Once we have done this,
    we need to start removing the directed edges from the nodes with no incoming edges.  If we remove all the incoming
    edges to a node during this step, we add this node to our queue.  Once we have removed all outgoing
    edges from the node from the queue, we remove that node from our map.  If at the end of this step we 
    have removed all the nodes from our map, we have a topological ordering.  If not, we do not have a 
    valid ordering.

    Another complexity analysis accounts for Nodes and Edges separately.  It takes O(N+E) to build the
    graph and O(N) to build initial queue.  Then O(N+E) to do the topological sort so the total running
    time is O(N+E).
*/

class Solution {
    class Node {
        List<Integer> out;
        int in;
        int course;
        
        public Node(int course){
            out = new ArrayList();
            in = 0;
            this.course = course;
        }
        public void addEdge(Node node){
            out.add(node.course);
            node.in++;
        }
    }
    
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        
        if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) return true;
        
        Map<Integer,Node> map = new HashMap();
        //build graph
        for(int[] e : prerequisites){
            if(!map.containsKey(e[0])) map.put(e[0],new Node(e[0]));
            Node src = map.get(e[0]);
            
            if(!map.containsKey(e[1])) map.put(e[1],new Node(e[1]));
            Node dst = map.get(e[1]);
            
            src.addEdge(dst);
        }
        
        //build initial queue for topological sort
        Queue<Integer> q = new LinkedList();
        for(int i : map.keySet()){
            if(map.get(i).in == 0){
                q.add(i);
            }
        }
        
        //topological sort
        while(!q.isEmpty()){
            Node node = map.get(q.poll());
            for(int c : node.out){
                Node curr = map.get(c);
                curr.in--;
                if(curr.in == 0) q.add(curr.course);
            }
            
            map.remove(node.course);
            
        }
        
        return map.size() == 0;
        
    }
}

/*
    We can alos do a dfs to find the cycles in the graph, however, we need more than just a visited
    list.  Seeing a node the we have already visited does not guarentee a cycle.  Many directed edges
    could point to a node and as long as that node doesn't point back to something that points to it,
    we don't have a cycle.  We need to keep track of a visited set of nodes (to avoid infinite recursion) and 
    a set of nodes that are currently on the stack.  When we start a dfs from a node, add it to the call stack
    and so on.  If we then see a node that is currently on the call stack, it means there is a directed path
    from the starting node back to itself, thus this means there is a cycle.  Therefore, we cannot finish
    all the courses.
    The analysis for this where n is numCourses is O(n^2) to build the graph (each node could point to every other node)
    and O(n^2) to dfs the graph as we are only touching each edge once and there could be as many as
    n^2 edges.  This gives a total running time of O(n^2) . 
    Another analysis is to consider the edges and node separately so it takes O(N+E) to build
    the graph and O(N+E) to dfs the graph.  This gives total running time of O(N+E).
*/
class Solution {
    
    boolean[] onStack;
    boolean[] visited;
    List<Integer>[] adj;
    boolean hasCycle = false;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) return true;
        onStack = new boolean[numCourses];
        visited = new boolean[numCourses];
        adj = new List[numCourses];
        
        //build adjacency lists for each ndoe
        for(int i = 0; i < numCourses; i++){
            adj[i] = new ArrayList();
        }
        for(int[] e : prerequisites){
            adj[e[0]].add(e[1]);
        }
        //dfs each node, keeping track of visited nodes
        for(int i = 0; i < numCourses; i++){
            if(!visited[i]) dfs(i);
            if(hasCycle) return false;
        }
        return true;
        
    }
    
    private void dfs(int i){
        visited[i] = true;
        onStack[i] = true;
        for(int dst : adj[i]){
            if(!visited[dst]) dfs(dst);
            else if(onStack[dst]){
                hasCycle = true; 
            }
        }
        onStack[i] = false;
    }
}
/*
Can also do the topo sort solution with an array instead of hashmap since courses are numbered 1....n-1
*/
class Solution {
    class Node {
        List<Integer> out;
        int in;
        int course;
        
        public Node(int course){
            out = new ArrayList();
            in = 0;
            this.course = course;
        }
        public void addEdge(Node node){
            out.add(node.course);
            node.in++;
        }
    }
    
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null || prerequisites.length == 0) return true;
        //build graph
        Node[] map = new Node[numCourses];
        for(int i = 0; i < numCourses; i++){
            map[i] = new Node(i);
        }
        for(int[] e : prerequisites){
            Node src = map[e[0]];
            Node dst = map[e[1]];
            src.addEdge(dst);
        }
        //build initial queue for topological sort
        Queue<Integer> q = new LinkedList();
        for(int i = 0; i<numCourses; i++){
            if(map[i].in == 0) q.add(i);
        }
        int removed = 0;
        //topological sort
        while(!q.isEmpty()){
            Node node = map[q.poll()];
            for(int c : node.out){
                Node curr = map[c];
                curr.in--;
                if(curr.in == 0) q.add(curr.course);
            }
            removed++;
        }
        return removed == numCourses;
        
    }
}