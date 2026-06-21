class Solution {
    public boolean[] visited;
    public int max;
    
    public int solution(int k, int[][] dungeons) {
        visited = new boolean[dungeons.length];
        DFS(k, 0, dungeons);
        return max;
    }
    
    public void DFS(int k, int lv, int[][] dungeons){
        if( lv >= dungeons.length){
            max = Math.max(max, lv);
            return;
        }
        
        for(int i = 0;i< dungeons.length;i++){
            if(visited[i]) continue;
            if(k>= dungeons[i][0]){
                visited[i] = true;
                DFS(k - dungeons[i][1], lv+1, dungeons);
                visited[i] = false;
            }else{
                max = Math.max(max, lv);
            }
        }
    }
}