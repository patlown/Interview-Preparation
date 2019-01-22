
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