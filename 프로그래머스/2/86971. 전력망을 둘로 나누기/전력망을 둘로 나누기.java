import java.util.*;

class Solution {
    public static int[][] adj;
    public int solution(int n, int[][] wires) {
        adj = new int[n+1][n+1];
        
        for(int[] w : wires){
            adj[w[0]][w[1]] = 1;
            adj[w[1]][w[0]] = 1;
        }
        
        int minGap = Integer.MAX_VALUE;
        
        for(int i = 0;i< wires.length;i++){
            int[] removed = wires[i];
            //remove
            adj[removed[0]][removed[1]] = 0;
            adj[removed[1]][removed[0]] = 0;
            
            boolean[] checked = new boolean[n+1];
            int[] counts = new int[2];
            int idx = 0;
            
            for(int j = 1;j<=n;j++){
                if(checked[j]) continue;
                counts[idx++] = BFS(j, checked);
            }
            
            minGap = Math.min(minGap, Math.abs(counts[0] - counts[1]));
            
            //recovery
            adj[removed[0]][removed[1]] = 1;
            adj[removed[1]][removed[0]] = 1;
        }
        
        return minGap;
    }
    
    public int BFS(int start, boolean[] checked){
        Queue<Integer> q = new LinkedList<>();
        int count = 0;
        
        checked[start] = true;
        q.offer(start);
        count++;
        
        while(!q.isEmpty()){
            int poll = q.poll();
            for(int i = 1;i< adj.length;i++){
                if(!checked[i] && adj[poll][i] == 1){
                    checked[i] = true;
                    q.offer(i);
                    count++;
                }
            }
        }
        
        return count;
    }
}