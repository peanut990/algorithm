import java.util.*;

class Solution {
    public int solution(String s) {
        int count = 0;
        StringBuffer sb = new StringBuffer(s);
        for(int i = 0;i< s.length();i++){
            if(check(sb.toString())) count++;
            
            sb.append(sb.charAt(0));
            sb.deleteCharAt(0);
        }
        return count;
    }
    
    public boolean check(String s){
        Stack<Character> stack = new Stack<>();
        
        for(int i = 0;i< s.length();i++){
            char c = s.charAt(i);
            if(c==']'){
                if(!stack.isEmpty() && stack.peek() == '['){
                    stack.pop();
                }else{
                    return false;
                }
            }else if(c=='}'){
                if(!stack.isEmpty() && stack.peek() == '{'){
                    stack.pop();
                }else{
                    return false;
                }
            }else if(c==')'){
                if(!stack.isEmpty() && stack.peek() == '('){
                    stack.pop();
                }else{
                    return false;
                }
            }else{
                stack.push(c);
            }
        }
        
        return stack.isEmpty() ? true : false;
    }
}