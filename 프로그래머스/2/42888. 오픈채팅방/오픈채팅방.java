import java.util.*;

class Solution {
    public String[] solution(String[] record) {
        HashMap<String, String> map = new HashMap<>();
        List<String> li = new ArrayList<>();
        String[] m = new String[]{"님이 들어왔습니다.", "님이 나갔습니다."};
        for(int i = 0;i< record.length;i++){
            String s = record[i];
            
            String[] sp = s.split(" +");
            if(sp[0].equals("Enter")){
                map.put(sp[1], sp[2]);

                li.add(sp[1] + " " + 0);
            }else if(sp[0].equals("Change")){
                map.put(sp[1], sp[2]);
            }else{
                li.add(sp[1] + " " + 1);
            }
        
        }
        
        String[] answer = new String[li.size()];
        
        for(int i = 0;i< li.size();i++){
            String[] splited = li.get(i).split(" ");
            answer[i] = map.get(splited[0]) + m[Integer.parseInt(splited[1])];
        }
        
        return answer;
    }
}