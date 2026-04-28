import java.util.*;

class Solution {
    public int solution(String begin, String target, String[] words) {
        HashSet<String> set = new HashSet<>();
        for(int i = 0;i<words.length;i++){
            set.add(words[i]);
        }
        
        if(!set.contains(target)) return 0;
        
        return BFS(begin, target, set);
    }
    
    public static int BFS(String begin, String target, HashSet<String> set){
        Queue<String> q = new LinkedList<>();
        HashSet<String> checked = new HashSet<>();
        q.offer(begin);
        checked.add(begin);
        
        int lv = 0;
        while(!q.isEmpty()){
            int size = q.size();
            for(int s = 0;s<size;s++){
                String curWord = q.poll();
                for(int i = 0; i< curWord.length();i++){
                    StringBuilder str = new StringBuilder(curWord);
                    
                    for(int c = 'a';c<='z';c++){
                        str.deleteCharAt(i);
                        str.insert(i,(char)c);
                        
                        
                        if(set.contains(str.toString())){
                            if(str.toString().equals(target)){
                                 return lv+1;
                            }
                            
                            if(checked.contains(str.toString())) continue;
                            
                            q.offer(str.toString());
                            checked.add(str.toString());
                        }
                    }
                }
            }
            
            lv++;
        }
        
        return 0;
    }
}