import java.util.*;

class Solution {
    public int solution(int n, int[] lost, int[] reserve) {
        HashSet<Integer> set = new HashSet<>();
        boolean[] losted = new boolean[n+1];
        
        for(int l: lost){
            losted[l] = true;
        }
        
        for(int r: reserve){
            set.add(r);
        }
        
        for(int i = 1;i<= n;i++){
            if(!losted[i]) continue;
            
            if(set.contains(i)){
                losted[i] = false;
                set.remove(i);
            }
        }
        
        for(int i = 1;i<= n;i++){
            if(!losted[i]) continue;
            
            if(set.contains(i - 1)){
                losted[i] = false;
                set.remove(i-1);
                continue;
            }
            
            if(set.contains(i + 1)){
                losted[i] = false;
                set.remove(i+1);
                continue;
            }
            
        }
        int count = 0;
        for(int i = 1;i<=n;i++){
            if(!losted[i]) count++;
        }
        
        return count;
    }
}