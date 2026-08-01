import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        HashMap<String,Integer> curMap = new HashMap<>();
        HashSet<String> typeSet = new HashSet<>();
        String[] settedGems = new String[gems.length + 1];
        
        for(String gem: gems){
            typeSet.add(gem);
        }
        
        for(int i = 0;i< gems.length;i++){
            settedGems[i+1] = gems[i];
        }
        gems = settedGems;
        
        int typeCount = typeSet.size();
        int left = 1;
        int right = 1;
        int[] minRange = new int[]{0, Integer.MAX_VALUE};
        
        while(right < gems.length){
            if(curMap.size() < typeCount){
                String gem = gems[right];
                curMap.put(gem, curMap.getOrDefault(gem, 0) + 1);
                right++;
            }else{
                if(curMap.size() == typeCount ){
                    if(minRange[1] - minRange[0] > right-1 - left){
                        minRange[0] = left;
                        minRange[1] = right-1;
                    }
                }
                
                String gem = gems[left];
                curMap.put(gem, curMap.get(gem) - 1);
                if(curMap.get(gem) <= 0){
                    curMap.remove(gem);
                }
                
                left++;
            }
        }
        
        //마지막
        while(left < gems.length){
            if(curMap.size() == typeCount && ((minRange[1] - minRange[0]) > (right-1 - left))){
                    minRange[0] = left;
                    minRange[1] = right-1;
            }
                
            String gem = gems[left];
            curMap.put(gem, curMap.get(gem) - 1);
            if(curMap.get(gem) <= 0){
                curMap.remove(gem);
            }

            left++;
        }
        
        return minRange;
    }
}

/*
HashSet 크기가 
-종류 보다 작으면 right++
-같거나 크면 left++

같은 경우
-길이 저장 min
-min 보다 작은 경우만 갱신
*/