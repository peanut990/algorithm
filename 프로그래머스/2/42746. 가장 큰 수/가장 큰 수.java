import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        List<String> li = new ArrayList<>();
        for(int num: numbers){
            li.add(String.valueOf(num));
        }
        
        Collections.sort(li, (a,b)->{
            int numA = Integer.parseInt(a+b);
            int numB = Integer.parseInt(b+a);
            
            return numB - numA;
        });
            
        StringBuilder sb = new StringBuilder();
        for(String s: li){
            sb.append(s);
        }
        
        if(li.get(0).equals("0")) return "0";
        
        return sb.toString();
    }
}
