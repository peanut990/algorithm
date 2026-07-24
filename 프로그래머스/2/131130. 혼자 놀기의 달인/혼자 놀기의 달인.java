import java.util.*;

class Solution {
    public int solution(int[] cards) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        boolean[] checked = new boolean[cards.length+1];
        
        for(int i = 0;i< cards.length;i++){
            int num = cards[i];
            int count = 0;
            while(!checked[num]) {
                count++;
                checked[num] = true;
                num = cards[num-1];
            }
            
            if(count > 0){
                pq.offer(count);
            }
        }
        
        if(pq.size() == 1){
            return 0;
        }
        
        int answer = 1;
        for(int i = 0;i< 2;i++){
            answer *= pq.poll();
        }
        return answer;
    }
}