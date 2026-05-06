class Solution {
    public long solution(int[] sequence) {
        long[] dp1 = new long[sequence.length];
        long[] dp2 = new long[sequence.length];
        
        int mul = 1;
        dp1[0] = sequence[0] * mul;
        dp2[0] = sequence[0] * -mul;
        
        long max = Math.max(dp1[0],dp2[0]);
        
        for(int i = 1;i<sequence.length;i++){
            mul *= -1;
            dp1[i] = Math.max(dp1[i-1] + sequence[i] * mul, sequence[i] * mul);
            dp2[i] = Math.max(dp2[i-1] + sequence[i] * -mul, sequence[i] * -mul);
            max = Math.max(max,Math.max(dp1[i],dp2[i]));
        }
        
        return max;
    }
}