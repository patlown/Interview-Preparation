/*
  Implementation: build graph to represent the network.  Run Dijkstra's to find the shortest
  path to very node in the graph.  We can then take the longest path found and return that as
  the amount of time necessary for all nodes in the graph to be reached.
  
  Edge Cases: if there are no edges, return -1. if there is exactly one edge, return 0 as the time
  from K to K is 0.

  Dijkstra Implementation tips: The node that we start Dijkstra's from will be the node we are
  calculating the distances from.  Keep a cost for each node that is relative to the cost needed
  to travel from our starting node to the node.  Set this cost to be infinity for all nodes except
  the starting node which is set to 0.  Whenever we have found a path to this node, check if our current
  cost is less than the node's current cost.  If so, update the cost for that node.  In Dijkstra's,
  we want to travel to the node's with the lowest cost first when choosing which neighbor to visit.  The best
  way to make this efficient is to keep a Priority Queue/Min Heap for our traversal instead of a 
  standard queue.  This way, when we insert our current node's neighbors, we can efficiently traverse
  to the next node with smallest cost.  To impelement this, we need a comparator for our nodes that 
  compares them based on their cost, we then pass that comparator to the instantiation of our pq and
  voila, we have an efficient structure for choosing our next destination.  
*/

class Solution {
    class NetworkNode {
        int label;
        List<NetworkNode> edges;
        List<Integer> weights;
        int cost;
        boolean visited;
        
        public NetworkNode(int label){
            this.label = label;
            edges = new ArrayList();
            weights = new ArrayList();
            cost = 0;
            visited = false;
        }
    }
    class NetworkNodeComparator implements Comparator<NetworkNode>{
        public int compare(NetworkNode node1, NetworkNode node2){
            return node1.cost - node2.cost;
        }
    }
    public int networkDelayTime(int[][] times, int N, int K) {
        if(times == null || times.length == 0){
            if(N == 1) return 0;
            return -1;
        }
        //build the graph and distance table
        NetworkNode[] graph = new NetworkNode[N+1];
        for(int i = 1; i < graph.length; i++){
            graph[i] = new NetworkNode(i);
            graph[i].cost = i == K ? 0 : Integer.MAX_VALUE;
        }
        for(int[] e: times){
            NetworkNode curr = graph[e[0]];
            curr.edges.add(graph[e[1]]);
            curr.weights.add(e[2]);
        }
        
        //begin a bfs-like traversal
        PriorityQueue<NetworkNode> pq = new PriorityQueue(new NetworkNodeComparator());
        pq.add(graph[K]);
        int cost;
        while(!pq.isEmpty()){
            NetworkNode curr = pq.poll();
            if(curr.visited == true) continue;
            cost = curr.cost;
            for(int i = 0; i < curr.edges.size(); i++){
                if(cost + curr.weights.get(i) < curr.edges.get(i).cost){
                    curr.edges.get(i).cost = cost + curr.weights.get(i);
                }
                pq.add(curr.edges.get(i));
            }
            curr.visited = true;
        }
        
        //all shortest paths from K should be up to date for every node
        int max = Integer.MIN_VALUE;
        for(int i = 1; i < graph.length; i++){
            if(graph[i].cost > max) max = graph[i].cost;
        }
        
        return max == Integer.MAX_VALUE ? -1 : max;
        
        
    }
}