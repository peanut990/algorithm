import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->{
            return a[0] - b[0];
        });
        for(int[] t: targets){
            pq.offer(t);
        }
        
        
        int answer = 1;
        int minX = Integer.MAX_VALUE;
        while(!pq.isEmpty()){
            int[] poll = pq.poll();
            if(poll[0] < minX){
                minX = Math.min(minX, poll[1]);
            }else{
                answer++;
                minX = poll[1];
            }
        }
        return answer;
    }
}

/*

------------------------
   --         ----
   -  -
 ------
*/