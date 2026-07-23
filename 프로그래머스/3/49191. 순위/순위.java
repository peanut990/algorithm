import java.util.*;

class Solution {
    public int solution(int n, int[][] results) {
        int[][] adj = new int[n+1][n+1];
        for(int[] r : results){
            adj[r[0]][r[1]] = 1;
        }
        
        for(int k = 1;k<=n;k++){
            for(int i = 1;i<=n;i++){
                for(int j = 1;j<=n;j++){
                    if(adj[i][k] == 1 && adj[k][j] == 1){
                        adj[i][j] = 1;
                    }
                }
            }
        }

        int answer = 0;
        for(int i = 1;i<=n;i++){
            int tmp = 0;
            for(int j = 1;j<=n;j++){
                if(adj[i][j] == 1 || adj[j][i]==1){
                    tmp++;
                }
            }
            if(tmp == n-1) answer++;
        }
        return answer;
    }
    
}

/*
4->3->
      ->2 ->5
    1->
   
4->3->2->5
4->2->5
1->2->5

0 1 2 3 4 5
1 - 1 - - 2
2 - - - - 1
3 - 1 - - 2
4 - 2 1 - 3
5 - - - - - 

*/