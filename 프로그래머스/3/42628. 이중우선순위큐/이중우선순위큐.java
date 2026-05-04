import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        
        for(int i = 0;i < operations.length;i++){
            String[] op = operations[i].split(" ");
            if(op[0].equals("I")){
                int num = Integer.parseInt(op[1]);
                map.put(num, map.getOrDefault(num,0) + 1);
            }else{
                if(map.size() == 0 ) continue;
                
                int c = Integer.parseInt(op[1]);
                int key = 0;
                if( c == -1){
                    key = map.firstKey();
                }else{
                    key = map.lastKey();
                }
            
                map.put(key, map.get(key) - 1);
                if( map.get(key) == 0 ) {
                    map.remove(key);
                }
            }
        }
        
        if( map.size() == 0 ) return new int[]{0,0};
        else return new int[]{map.lastKey(),map.firstKey()};
    }
    
}