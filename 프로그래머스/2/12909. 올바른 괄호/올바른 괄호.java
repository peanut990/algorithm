import java.util.*;

class Solution {
    boolean solution(String s) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0;i<s.length();i++){
            if(!stack.isEmpty() && s.charAt(i) == ')' && stack.peek() == '('){
                stack.pop();
                continue;
            }
            
            stack.push(s.charAt(i));
        }
        
        if(stack.size() > 0) return false;
        else return true;
    }
}