class Solution {
    public static boolean[] visited;
    
    public int solution(int n, int[][] computers) {
        visited = new boolean[n];
        int count = 0;
        for(int i = 0;i<n;i++){
            if(visited[i]) continue;
            
            count++;
            visited[i] = true;
            DFS(i,computers);
        }
        
        return count;
    }
    
    public static void DFS(int c, int[][] computers){
        for(int i = 0;i<computers.length;i++){
            if(i == c) continue;
            
            if(computers[c][i] == 1 && !visited[i]){
                visited[i] = true;
                DFS(i,computers);
            }
        }
    }
}