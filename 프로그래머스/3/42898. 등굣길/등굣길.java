import java.util.*;

class Solution {
    
    public int solution(int m, int n, int[][] puddles) {
        int[][] map = new int[n][m];
        
        for(int[] loc : puddles){
            map[loc[1] -1][loc[0] -1] = -1;
        }
        
        map[0][0] = 1;
        
        for(int y = 0;y< n;y++){
            for(int x = 0 ;x< m;x++){
                if(map[y][x] == -1) continue;
                
                if( y > 0 && map[y-1][x] != -1){
                    map[y][x] = (map[y][x] + map[y-1][x]) % 1_000_000_007;
                }
                
                if(x >0 && map[y][x-1] != -1){
                    map[y][x] = (map[y][x] + map[y][x-1]) % 1_000_000_007;
                }
            }
        }
        
        return map[n-1][m-1];
    }
    
}