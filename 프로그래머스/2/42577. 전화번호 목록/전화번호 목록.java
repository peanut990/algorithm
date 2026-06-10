import java.util.*;

class Solution {
    public boolean solution(String[] phone_book) {
        HashSet<String> set = new HashSet<>();
        for(String s: phone_book){
            set.add(s);
        }
        
        for(String s: phone_book){
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i<s.length()-1;i++){
                sb.append(s.charAt(i));
                if(set.contains(sb.toString())) return false;
            }
        }
        
        return true;
    }
}