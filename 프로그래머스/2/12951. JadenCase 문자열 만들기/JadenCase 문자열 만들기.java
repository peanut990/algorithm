import java.util.*;

class Solution {
    public String solution(String s) {
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        
        for(int i = 0;i< s.length();i++){
            char c = s.charAt(i);
            if(c==' '){
                isFirst = true;
                sb.append(String.valueOf(c));
                continue;
            }
            
            if(isFirst){
                if(c>='a' && c<='z'){
                    c -= 'a' - 'A';
                } 
                isFirst = false;
            }
            
            sb.append(String.valueOf(c));
        }
        
        return sb.toString();
    }
}