import java.util.*;

class Solution {
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        List<List<Integer>> ad = new ArrayList<>();
        for(int i = 0;i<=n;i++){
            ad.add(new ArrayList<>());
        }
        
        for(int i = 0;i<roads.length;i++){
            int l = roads[i][0];
            int r = roads[i][1];
            
            ad.get(l).add(r);
            ad.get(r).add(l);
        }
        
        int[] answer = new int[sources.length];
        int[] dist = BFS(destination,ad,n);
        
        for(int i = 0;i<sources.length;i++){
            int s = sources[i];
            
            answer[i] = dist[s];
        }
        
        return answer;
    }
    
    public int[] BFS(int destination, List<List<Integer>> ad,int n){
        int[] dist = new int[n+1];
        Arrays.fill(dist,-1);
        Queue<Integer> q = new LinkedList<>();
        
        q.offer(destination);
        dist[destination] = 0;
        
        int lv = 1;

        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0;i< size;i++){
                int curStart = q.poll();
                
                for(int endIdx = 0; endIdx < ad.get(curStart).size();endIdx++){
                    int end = ad.get(curStart).get(endIdx);
            
                    if(dist[end] != -1) continue;
                    
                    q.offer(end);
                    dist[end] = lv;
                }
            }
            lv++;
        }
        
        return dist;
    }
}