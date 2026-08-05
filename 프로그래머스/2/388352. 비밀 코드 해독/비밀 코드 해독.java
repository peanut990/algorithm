import java.util.*;

class Solution {
    static Set<Integer> set = new HashSet<>();
    static int N;
    static int[][] Q;
    static int[] ANS;
    static int result;
    public int solution(int n, int[][] q, int[] ans) {
        Q = q;
        N = n;
        ANS = ans;
        
        Combi(1);
        
        return result;
    }
    
    public void Combi(int start){
        if(set.size() == 5){
            boolean found = true;
            for(int i = 0;i< Q.length;i++){
                int[] curQ = Q[i];
                int count = 0;
                
                for(int n : curQ){
                    if(set.contains(n)){
                        count++;
                    }
                }
                
                if(count != ANS[i]) {
                    found = false;
                    break;
                }
            }
            
            if(found){
                result++;
            }
            
            return;
        }
        
        for(int i = start; i<=N;i++){
            set.add(i);
            Combi(i+1);
            set.remove(i);
        }
    }
}

/*
30c5 * 5 * 10

*/