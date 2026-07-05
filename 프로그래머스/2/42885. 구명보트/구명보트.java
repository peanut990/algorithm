import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        List<Integer> li = new ArrayList<>();
        for(int p : people){
            li.add(p);
        }
        Collections.sort(li, Collections.reverseOrder());
        int left = 0;
        int right = li.size()-1;
        int answer = 0;
        
        while(left <= right){
            int sum = li.get(left++);
            
            if(sum + li.get(right) <= limit ){
                sum += li.get(right--);
            }
            
            answer++;
        }
        
        return answer;
    }
}
