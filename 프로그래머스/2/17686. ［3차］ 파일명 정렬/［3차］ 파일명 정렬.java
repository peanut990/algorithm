import java.util.*;

class Solution {
    class File{
        String originName;
        String head;
        String tail;
        
        File(String originName, String head, String tail){
            this.originName = originName;
            this.head = head;
            this.tail = tail;
        }
    }
    public String[] solution(String[] files) {
        List<File> li = new ArrayList<>();
        
        for(int j = 0;j< files.length; j++){
            String fileName = files[j];

            StringBuilder head = new StringBuilder();
            StringBuilder tail = new StringBuilder();

            boolean onTail = false;
            for(int i = 0;i< fileName.length();i++){
                if(fileName.charAt(i) >= '0' && fileName.charAt(i) <= '9'){
                    onTail = true;
                    tail.append(fileName.charAt(i));
                    continue;
                }

                if(onTail){
                   break; 
                }
                
                head.append(fileName.charAt(i));
            }

            li.add(new File(fileName, head.toString(), tail.toString()));
        }
        
        Collections.sort(li, (a,b)->{
            if(!a.head.toUpperCase().equals(b.head.toUpperCase())){
                 return a.head.toUpperCase().compareTo(b.head.toUpperCase());   
            }
                
            return Integer.parseInt(a.tail) - Integer.parseInt(b.tail);
        });
        
        String[] answer = new String[li.size()];
        for(int i = 0;i< li.size();i++){
            answer[i] = li.get(i).originName;
        }
        
        return answer;
    }
}