class Solution {
    public int solution(int[][] triangle) {
        int size = triangle[triangle.length-1].length;
        
        int[][] dp = new int[size][size];
        dp[0][0] = triangle[0][0];
        
        for(int i = 0;i< size-1;i++){
            for(int j = 0;j<triangle[i].length;j++){
                dp[i+1][j] = Math.max(dp[i+1][j], dp[i][j] + triangle[i+1][j]);
                dp[i+1][j+1] = Math.max(dp[i+1][j+1], dp[i][j] + triangle[i+1][j+1]);
            }
        }
        
        int max = 0;
        for(int n : dp[size-1]){
            max = Math.max(max, n);
        }
        return max;
    }
}