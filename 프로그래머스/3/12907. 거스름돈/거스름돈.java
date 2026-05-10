class Solution {
    public int solution(int n, int[] money) {
        int[] dp = new int[n+1];
        dp[0] = 1;
            for(int j = 0;j<money.length;j++){
                int curM = money[j];
                for(int i = curM;i <= n;i++){
                    dp[i] += dp[i-curM];
            }
        }
        
        return dp[n];
    }
}