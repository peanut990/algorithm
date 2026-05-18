import java.util.*;

class Solution {
    public int solution(int k, int[] tangerine) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for(int i : tangerine){
            map.put(i, map.getOrDefault(i,0) + 1);
        }
        
        List<Map.Entry<Integer,Integer>> li = new ArrayList<>(map.entrySet());
        Collections.sort(li,(a,b)->{
            return b.getValue() - a.getValue();
        });
        
        int count = 0;
        for(Map.Entry<Integer,Integer> ent: li){
            k -= ent.getValue();
            count++;
            if(k <= 0){
                break;
            }
        }
        return count;
    }
}