import java.util.*;
class Solution {
    public int[] solution(int target) {
        int[][] dp = new int[target+1][3];

        for(int i = 1;i<=target;i++){
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for(int i = 0;i<=target;i++){
            for(int j = 1;j<=20;j++){
                int count = 0;
                int single = 0;
                int bull = 0;
                for(int k = 1;k<=3;k++){
                    int score = j * k;
                    if(i - score < 0 ) continue;
                    
                    count = dp[i-score][0] + 1;
                    single = dp[i-score][1];
                    if(k == 1) single++;
                    
                    bull = dp[i-score][2];
                    
                    if(dp[i][0] < count ) continue;
                    if (dp[i][0] == count && dp[i][1] + dp[i][2] >= single + bull ) continue;
                    
                    dp[i] = new int[]{count,single,bull};
                }
            }
            // bull
            int score = 50;
            if(i - score < 0 ) continue;

            int count = dp[i-score][0] + 1;
            int single = dp[i-score][1];
            int bull = dp[i-score][2] + 1;

            if(dp[i][0] < count ) continue;
            if (dp[i][0] == count && dp[i][1] + dp[i][2] >= single + bull ) continue;

            dp[i] = new int[]{count,single,bull};
        }
        
        return new int[]{dp[target][0],dp[target][1]+dp[target][2]};
    }
}

/*
  1     2      3     4   5 .... 19    20       50
1 2 3  2 4 6  3 6 9                20 40 60    50

0  1     2     3     4   5 ..  21
 1 1 0 2 2 0
       1 1 0
*/