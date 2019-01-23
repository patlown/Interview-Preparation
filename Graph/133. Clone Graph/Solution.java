/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */

 /*
    First approach: Keep a hashmap that maps old nodes to new nodes.  DFS through the graph to 
    create copies.  Check if we've seen the nodes before. Similar approach to creating a deep copy
    of a linked list with random pointer
    Edges Cases: empty graph, return null
 */
public class Solution {
    Map<UndirectedGraphNode,UndirectedGraphNode> map;
    Set<UndirectedGraphNode> visited;
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node == null) return node;
        map = new HashMap();
        //add the head node to the map and dfs each of its neighbors
        visited = new HashSet();
        UndirectedGraphNode cloneHead = new UndirectedGraphNode(node.label);
        map.put(node,cloneHead);
        dfs(node,cloneHead);
        return cloneHead;
        
        
    }
    private void dfs(UndirectedGraphNode old, UndirectedGraphNode clone){
        if(visited.contains(old)) return;
        visited.add(old);
        for(UndirectedGraphNode n : old.neighbors){
            if(!map.containsKey(n)) map.put(n,new UndirectedGraphNode(n.label));
            UndirectedGraphNode curr = map.get(n);
            clone.neighbors.add(curr);
            dfs(n,curr);
        }
    }
}
/*
    We can also do this iteratively with a BFS approach.  In this appraoch we keep a q of nodes
    that we need to visit.  Once a node is polled from the queue, we add all its neighbors and make
    the copies if necessary.  Everytime we add a neighbor to a node, we add it to the queue if it has
    yet to be visited.
    Edge Cases: empty graph, return null
*/
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node == null) return node;
        Map<UndirectedGraphNode,UndirectedGraphNode> map = new HashMap();
        Set<UndirectedGraphNode> visited = new HashSet();
        Queue<UndirectedGraphNode> q = new LinkedList();
        
        UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
        map.put(node,clone);
        q.add(node);
        
        while(!q.isEmpty()){
            UndirectedGraphNode old = q.poll();
            visited.add(old);
            if(!map.containsKey(old)) map.put(old,new UndirectedGraphNode(old.label));
            UndirectedGraphNode copy = map.get(old);
            
            for(UndirectedGraphNode n : old.neighbors){
                if(!map.containsKey(n)) map.put(n,new UndirectedGraphNode(n.label));
                copy.neighbors.add(map.get(n));
                if(!visited.contains(n)) q.add(n);
            }
        }
        
        return clone;
    }
}