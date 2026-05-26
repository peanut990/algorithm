import java.util.*;

class Solution {
    public int solution(int[] order) {
        int answer = 0;
        
        Stack<Integer> stack = new Stack<>();
        int idx = 0;
        int curNum = 1;
        
        while(answer < order.length) {
            // 메인 컨테이너와 순서 일치
            if(curNum == order[idx]){
                answer++;
                idx++;
                curNum++;
                continue;
            }
            
            // 보조 컨테이너와 순서 일치
            if(!stack.isEmpty() && stack.peek() == order[idx]){
                stack.pop();
                answer++;
                idx++;
                continue;
            }
            
            if(curNum > order[idx] ){
                break;
            }
            
            
            stack.push(curNum);
            curNum++;
        }
        return answer;
    }
}

