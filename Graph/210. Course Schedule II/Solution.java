/*
    Very similar idea to the previous course schedule, although, this time we need to return the ordering
    rather than whether or not the ordering exists.  In this case, we can't just use a dfs to check for a cycle
    because that won't tell us a valid ordering.  We have to go with a topological sort solution
    such that we can build our return list as we sort through the nodes.  Everytime we remove all the 
    outgoing edges from a node, we need to add it the end of the list.  Since an edge between source and destination
    means that we must complete the destination before we complete the source, if we have a node with no
    incoming edges and only out going.  We need to complete that node only after all its directed edges nodes
    are completed.  Thus, we must add those with no incoming to the end of the list rather than the start.  Keep an index
    and decrement it as we add nodes.
    It is O(V+E) just as the other solution.
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
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        if(prerequisites == null || prerequisites.length == 0){
            if(numCourses != 0){
                for(int i = 0; i < numCourses; i++){
                    res[i] = i;
                }
                return res;
            }
            return new int[0];
        }
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
        
        int index = numCourses-1;
        //topological sort
        while(!q.isEmpty()){
            Node node = map[q.poll()];
            for(int c : node.out){
                Node curr = map[c];
                curr.in--;
                if(curr.in == 0) q.add(curr.course);
            }
            res[index--] = node.course;
        }
        return index == -1 ? res : new int[0];
        
    }
}

/*
    Can also do a dfs solution where we do a dfs from each node and once we are done dfsing from
    the current node we add it to the solution.  This will result in nodes wihout any dependencies
    being added to the ordering first which is correct.  And any node that depends on another node will have
    all its dependencies added by the time it is done with its dfs.  
    Implementation:  Need a call stack value such as the first course schedule to ensure no cycles, we need
    a result that can be built during iteration and we need to mark nodes as visited.

    This solution needs O(V+E) time to complete

*/