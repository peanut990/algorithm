import java.util.*;

class Solution {
    public int[] solution(int n, String[] words) {
        HashSet<String> set = new HashSet<>();
        String firstWord = words[0];
        set.add(firstWord);
        
        char lastChar = firstWord.charAt(firstWord.length()-1);
        
        for(int i = 1;i < words.length;i++){
            String s = words[i];
            char firstChar = s.charAt(0);
            
            if(firstChar != lastChar){
                return new int[]{i%n+1, i/n+1};
            }
            
            if(set.contains(s)){
                return new int[]{i%n+1, i/n+1};
            }
            
            set.add(s);
            lastChar = s.charAt(s.length()-1);
        }
        
        return new int[]{0,0};
    }
}

