import java.util.*;
class Solution {
    public int solution(int n) {
        String bs = Integer.toBinaryString(n);
        int count = 0;
        for(int i = 0;i<bs.length();i++){
            if(bs.charAt(i) == '1'){
                count++;
            }
        }
        
        int curNum = n;
        while(true){
            curNum++;
            String tmp = Integer.toBinaryString(curNum);
            int curCount = 0;
            for(int i = 0;i<tmp.length();i++){
                if(tmp.charAt(i) == '1'){
                    curCount++;
                }
            }
            
            if(curCount == count) break;
        }
       
        return curNum;
    }
}