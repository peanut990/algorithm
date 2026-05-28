import java.util.*;

class Solution {
    public static int min = Integer.MAX_VALUE;
    public int solution(int storey) {
        //6이상 -> 반올림
        DFS(storey, 0);
        
        return min;
    }
    
    public void DFS(int storey, int count){
        if(storey == 0){
            min = Math.min(min, count);
            return;
        }
        
        if (storey < 10) {
            min = Math.min(min, count + (10 - storey) + 1);
            min = Math.min(min, count + storey);
            return;
        }
        
        // 반올림
        DFS(storey/10 + 1, count + 10 - (storey % 10));
        // 반올림 x
        DFS(storey/10, count + (storey % 10));
    }
}