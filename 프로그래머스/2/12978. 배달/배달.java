import java.util.*;

class Solution {
    class Edge implements Comparable<Edge>{
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
    public int solution(int N, int[][] road, int K) {
        List<List<Edge>> adj = new ArrayList<>();
        for(int i = 0;i<= N;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int[] r: road){
            adj.get(r[0]).add(new Edge(r[1],r[2]));
            adj.get(r[1]).add(new Edge(r[0],r[2]));
        }
        
        // dijikstra
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int[] dist = new int[N+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;
        pq.offer(new Edge(1,0));
        
        while(!pq.isEmpty()){
            Edge cur = pq.poll();
            
            if(cur.cost > dist[cur.vertex]) continue;
            for(Edge next : adj.get(cur.vertex)){
                if(dist[next.vertex] > cur.cost + next.cost){
                    dist[next.vertex] = cur.cost + next.cost;
                    pq.offer(new Edge(next.vertex, cur.cost + next.cost));
                }
            }
        }
        
        
        int answer = 0;
        for(int d : dist){
            if( d <= K){
                answer++;
            }
        }
        return answer;
    }
}