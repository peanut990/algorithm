import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        List<List<Integer>> adj = new ArrayList<>();
        for(int i = 0;i<=n;i++){
            adj.add(new ArrayList<>());
        }
        
        for(int[] e: edge){
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        
        return BFS(1,n,adj);
    }
    
    public int BFS(int s, int n, List<List<Integer>> adj){
        Queue<Integer> q = new LinkedList<>();
        boolean[] checked = new boolean[n+1];
        
        q.offer(s);
        checked[s] = true;
        
        int size = 0;
        while(!q.isEmpty()){
            for(int i: q){
                System.out.print(i + " ");
            }
            System.out.println();
            size = q.size();
            
            for(int i = 0;i< size;i++){
                int poll = q.poll();
                //인접 노드 순회
                for(int v : adj.get(poll)){
                    if(checked[v]) continue;
                    checked[v] = true;
                    q.offer(v);
                }
            }
        }
        return size;
    }
}