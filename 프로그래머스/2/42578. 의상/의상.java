import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        HashMap<String, Integer> map = new HashMap<>();
        
        for(String[] cloth : clothes){
            map.put(cloth[1], map.getOrDefault(cloth[1],1)+1);
        }
        
        int answer = 1;
        for(String key : map.keySet()){
            answer *= map.get(key);
        }
        
        return answer-1;
    }
}