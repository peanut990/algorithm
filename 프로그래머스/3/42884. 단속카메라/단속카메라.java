import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        Arrays.sort(routes, (a,b)->{
            return a[1] - b[1];
        });
        
        int answer = 0;
        int lastEnd = Integer.MIN_VALUE;
        for(int i = 0;i< routes.length;i++){
            int start = routes[i][0];
            int end = routes[i][1];
            
            if(start > lastEnd){
                lastEnd = end;
                answer++;
            }
            
        }
        
        return answer;
    }
}

/*
    ---
--
 ----
*/