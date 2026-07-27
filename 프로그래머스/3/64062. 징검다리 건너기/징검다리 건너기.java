class Solution {
    public int solution(int[] stones, int k) {
        int left = 0;
        int right = 200_000_000;
        
        int answer = 0;
        while(left <= right){
            int mid = (left+ right)/2;
            //순회
            int count = 0;
            int maxCount = 0; // 넘여야되는 돌 수
            for(int i = 0;i< stones.length;i++){
                if(stones[i] < mid){
                    count++;
                    continue;
                }
                
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
            
            maxCount = Math.max(maxCount, count);
            if(maxCount < k){
                left = mid + 1;
                answer = mid;
            }else{
                right = mid - 1;
            }
        }
        return answer;
    }
}