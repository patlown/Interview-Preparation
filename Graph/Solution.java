/*
    First thought: Keep a map of all known equations which we can access efficiently. To find the
    value of a query we can dfs through our map keeping tracking of current multiple.

    Implementation: Build our graph as a map with key: 'string' and value: 'map with key: string and value: double'
    this way, we will have an efficient structure for looking up the numerators of our query which will be stored
    in the outer map and the denominators of our query which will be stored in the inner map.  First step, build
    this graph making sure to include inverses and self divisions.  With inverses and self included we never have
    to worry about calculating an inverse again or swapping denominators with numerators.  Once we have
    built the graph, we can then do a dfs search to determine the results of our queries.  
    DFS implementation: if our the query we are looking for matches one in our graph, then we can
    just return it, else we need to dfs through the denominators mapped to our numerator.  When we do
    this, we need to keep track of the numerator/denominator answer and then multiply by the potential
    partial answer that find from dfsing furhter.  In the next dfs our query now has the denominator swapped with
    the numerator.  The code sums up the logic best.
*/


class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        double[] results = new double[queries.length];
        //edge cases
        if(equations == null || equations.length == 0 || values == null || values.length == 0 || queries == null || queries.length == 0)
            return results;
        //build the graph
        Map<String,Map<String,Double>> graph = new HashMap();
        for(int i = 0; i < equations.length; i++){
            String numer = equations[i][0];
            String denom = equations[i][1];
            if(!graph.containsKey(numer)){
                graph.put(numer,new HashMap());
                graph.get(numer).put(numer,1.0);
            }
            graph.get(numer).put(denom,values[i]);
            
            if(!graph.containsKey(denom)){
                graph.put(denom,new HashMap());
                graph.get(denom).put(denom,1.0);
            }
            graph.get(denom).put(numer,(double)1/values[i]);
        }
        //solve our equations
        int index = 0;
        for(String[] query : queries){
            results[index++] = dfs(query,graph,new HashSet());
        }
        
        return results;
        
    }
    private double dfs(String[] query, Map<String,Map<String,Double>> graph, Set<String> visited){
        String numer = query[0];
        String denom = query[1];
        if(!graph.containsKey(numer)) return -1;
        visited.add(numer);
        Map<String,Double> adj = graph.get(numer);
        
        if(adj.containsKey(denom)) return adj.get(denom);
        for(String s : adj.keySet()){
            if(!visited.contains(s)){
                query[0] = s;
                double result = adj.get(s);
                double partial = dfs(query,graph,visited);
                if(partial == -1) continue;
                else return result*partial;
            }
        }
        
        return -1;
        
    }
    
}