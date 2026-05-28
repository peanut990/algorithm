import java.util.*;
class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> li = new ArrayList<>();
        
        int count = -1;
        int max = 0;
        int finished = 0;
        
        for(int i = 0;i<progresses.length;i++){
            int remain =  100 - progresses[i];
            int day = remain / speeds[i];
            if(remain % speeds[i] != 0){
                day++;
            }
            
            
            if(day > max){
                if(finished != 0){
                    li.add(finished);
                }
                
                finished = 0;
                max = day;
            }
            
            finished++;
        }
        
        if(finished > 0){
            li.add(finished);
        }
        
        int[] answer = new int[li.size()];
        for(int i = 0;i < answer.length;i++){
            answer[i] = li.get(i);
        }
        return answer;
    }
}

