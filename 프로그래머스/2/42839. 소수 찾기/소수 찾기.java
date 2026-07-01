import java.util.*;

class Solution {
    public static HashSet<Integer> set = new HashSet<>();
    public static boolean[] checked;
    public int solution(String numbers) {
        checked = new boolean[numbers.length()];
        DFS(numbers, new StringBuilder("0"));
        System.out.println(set);
        
        return set.size();
    }
    
    public void DFS(String numbers, StringBuilder sb){
        if(isPrime(Integer.parseInt(sb.toString()))) set.add(Integer.parseInt(sb.toString()));
        
        for(int i =0;i< numbers.length();i++){
            if(checked[i]) continue;
            checked[i] = true;
            
            DFS(numbers, sb.append(numbers.charAt(i)));
            
            sb.deleteCharAt(sb.length() -1);
            checked[i] = false;
        }
    }
    
    public boolean isPrime(int num){
        if(num < 2) return false;
        
        for(int i = 2;i< num;i++){
            if(num % i == 0) return false;
        }
        
        return true;
    }
}


/*
checked


0 1 2 3 4

0  2
01 2

*/