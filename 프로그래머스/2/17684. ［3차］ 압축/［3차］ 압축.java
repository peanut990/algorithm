import java.util.*;

class Solution {
    public int[] solution(String msg) {
        HashMap<String,Integer> map = new HashMap<>();
        int num = 1;
        for(char c = 'A';c<='Z';c++){
            map.put(String.valueOf(c),num++);
        }
        List<Integer> li = new ArrayList<>();
        
        for(int sIdx = 0;sIdx<msg.length();){
            StringBuffer sb = new StringBuffer(String.valueOf(msg.charAt(sIdx)));
            int tmpIdx = sIdx;
            while(map.containsKey(sb.toString())){
                tmpIdx++;
                if(tmpIdx >= msg.length()) break;
                
                sb.append(msg.charAt(tmpIdx));
                //System.out.println("append");
            }
            
            if(map.containsKey(sb.toString())){
                li.add(map.get(sb.toString()));
                sIdx += sb.length();
                
            }else{
                map.put(sb.toString(), num++);
                String s = sb.deleteCharAt(sb.length()-1).toString();

                li.add(map.get(s));
                
                sIdx += s.length();
            }
        }
        
        int[] answer = new int[li.size()];
        for(int i = 0;i< li.size();i++){
            answer[i] = li.get(i);
        }
        
        return answer;
    }
}