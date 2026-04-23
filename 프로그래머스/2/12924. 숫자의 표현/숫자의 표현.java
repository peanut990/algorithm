class Solution {
    public int solution(int n) {
        int[] ary = new int[n+1];
        
        for(int i = 1;i<= n;i++){
            ary[i] = i;
        }
        
        int lt = 1,rt = 1;
        int sum = 0;
        int count = 0;
        while(lt<=n){
            if(sum == n){
                count++;
                sum -= ary[lt++];
            }else if(sum < n){
                sum += ary[rt++];
            }else{
                sum -= ary[lt++];
            }
        }
        
        return count;
    }
}