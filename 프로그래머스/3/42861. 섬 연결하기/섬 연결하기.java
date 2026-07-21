import java.util.*;

class Solution {
    class Edge implements Comparable<Edge> {
        int vertex;
        int cost;
        
        Edge(int vertex, int cost){
            this.vertex = vertex;
            this.cost = cost;
        }
        
        public int compareTo(Edge e){
            return this.cost - e.cost;
        }
    }
    
    public int solution(int n, int[][] costs) {
        int[][] adj = new int[n][n];
        for(int i = 0;i<costs.length;i++){
            int[] cost = costs[i];
            adj[cost[0]][cost[1]] = cost[2];
            adj[cost[1]][cost[0]] = cost[2];
        }
        
        return mst(0,n,adj);
    }
    
    public int mst(int s, int n,int[][] adj){
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] checked = new boolean[n];
        int totalCost = 0;
        
        pq.offer(new Edge(s,0));
        
        while(!pq.isEmpty()){
            Edge poll = pq.poll();
            int vertex = poll.vertex;
            int cost = poll.cost;
            
            if(checked[vertex]) continue;
            checked[vertex] = true;
            
            totalCost += cost;
            for(int next = 0;next < n; next++){
                if(adj[vertex][next] <= 0 || checked[next]) continue;
                
                pq.offer(new Edge(next, adj[vertex][next]));
            }
        }
        return totalCost;
    }
}
