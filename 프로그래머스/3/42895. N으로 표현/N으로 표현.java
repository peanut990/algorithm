import java.util.*;

class Solution {
    public int solution(int N, int number) {
        HashSet<Integer>[] dp = new HashSet[9];
        
        for(int i = 0;i<9;i++){
            dp[i] = new HashSet();
        }
        
        int tmp = N;
        dp[1].add(tmp);
        if(dp[1].contains(number)){
                return 1;
            }
        
        for(int i = 2;i<9;i++){
            tmp = tmp*10 + N;
            dp[i].add(tmp);
            
            for(int j = 1;j<i;j++){
                for(int a: dp[j] ){
                    for(int b: dp[i-j]){
                        for(int t = 0;t<4;t++){
                            int nextNum = cal(a,b,t);
                            dp[i].add(nextNum);
                        }
                    }
                }
            }
            
            
            if(dp[i].contains(number)){
                return i;
            }
        }
        
        
        return -1;
    }
    
    public int cal(int a, int b, int type){
        if(type == 0) return a + b;
        else if(type == 1) return a - b;
        else if (type==2 && b!=0) return a/b;
        else return a * b;
        
    }
}