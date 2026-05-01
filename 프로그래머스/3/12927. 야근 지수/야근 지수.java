import java.util.*;

class Solution {
    public long solution(int n, int[] works) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int work : works){
            pq.offer(work);
        }
        
        for(int i = 0;i<n;i++){
            int tmp = pq.poll();
            if(tmp - 1 < 0){
                pq.offer(0);
            }else{
                pq.offer(tmp-1);
            }
        }
        
        long answer = 0;
        for(int work : pq){
            answer += work * work;
        }
        
        return answer;
    }
}