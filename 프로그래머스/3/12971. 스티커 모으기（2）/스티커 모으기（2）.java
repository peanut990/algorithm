class Solution {
    public int solution(int sticker[]) {
        if(sticker.length == 1){
            return sticker[0];
        }
        if(sticker.length == 2){
            return Math.max(sticker[0],sticker[1]);
        }
        
        int[] dp = new int[sticker.length];
        
        dp[0] = sticker[0];
        dp[1] = sticker[0];
        int max = 0;
        for(int i = 2;i< sticker.length-1;i++){
            dp[i] = Math.max(dp[i-2]+sticker[i],dp[i-1]);
            max= Math.max(max,dp[i]);
        }
        
        dp = new int[sticker.length];
        dp[1] = sticker[1];
        for(int i = 2;i< sticker.length;i++){
            dp[i] = Math.max(dp[i-2]+sticker[i],dp[i-1]);
            max= Math.max(max,dp[i]);
        }
        
        return max;
        
    }
}