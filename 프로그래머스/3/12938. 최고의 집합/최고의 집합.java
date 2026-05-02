class Solution {
    public int[] solution(int n, int s) {
        int[] answer = new int[n];
        if(n > s) return new int[]{-1};
        
        for(int i = 0;i<n;i++){
            answer[i] = s/ (n-i);
            s -= answer[i];
        }
        
        return answer;
    }
}