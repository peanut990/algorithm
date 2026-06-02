import java.util.*;

class Solution {
    public int solution(int[] priorities, int location) {
        Queue<Integer> q = new LinkedList<>();
        for(int i = 0;i< priorities.length;i++){
            q.offer(i);
        }
        
        int count = 1;
        while(true){
            int curIdx = q.poll();
            boolean flag = true;
            for(int idx : q){
                if(priorities[idx] > priorities[curIdx]){
                    q.offer(curIdx);
                    flag = false;
                    break;
                }
            }
            if(flag){
                if(curIdx == location) break;
                count++;
            }
        }
        return count;
    }
}

