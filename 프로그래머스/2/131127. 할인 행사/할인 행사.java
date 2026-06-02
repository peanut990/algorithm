import java.util.*;
class Solution {
    public int solution(String[] want, int[] number, String[] discount) {
        Map<String, Integer> countMap = new HashMap<>();
        int answer = 0;
        
        for(int i = 0;i< 10;i++){
            countMap.put(discount[i], countMap.getOrDefault(discount[i], 0) + 1);
        }
        
        boolean flag = true;
        for(int i = 0;i< want.length;i++){
            String w = want[i];
            int goal = number[i];
            
            if(!countMap.containsKey(w) || countMap.get(w) != goal){
                flag = false;
                break;
            }
        }
        if(flag) answer++;
        
        for(int i = 10;i<discount.length;i++){
            countMap.put(discount[i-10], countMap.getOrDefault(discount[i-10], 0) - 1);
            
            countMap.put(discount[i], countMap.getOrDefault(discount[i], 0) + 1);
            
            flag = true;
            for(int j = 0;j< want.length;j++){
                String w = want[j];
                int goal = number[j];

                if(!countMap.containsKey(w) || countMap.get(w) != goal){
                flag = false;
                break;
                }
            }
            if(flag) answer++;
        }
        
        return answer;
    }
    
}