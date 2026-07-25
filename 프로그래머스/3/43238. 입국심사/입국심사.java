class Solution {
    public long solution(int n, int[] times) {
        long left = 0;
        long right = 1_000_000_000L * 1_000_000_000L;
        long answer = 0;
        
        System.out.println(right);
        while(left <= right){
            long mid = (left+right)/2;
            long count = 0;

            for(int i = 0;i< times.length;i++){
                count += mid/times[i];
                if(count > n){
                    break;
                }
            }
            
            // System.out.println(mid + " " + count);
            if(count >= n){
                right = mid -1;
                answer = mid;
            }else{
                left = mid + 1;
            }
        }
        return answer;
    }
}

/*
1 1 1 -> 2분 8까지
         1분 3까지
20 -> 30
21-23 -> 28-30 
29 n = 6
*/