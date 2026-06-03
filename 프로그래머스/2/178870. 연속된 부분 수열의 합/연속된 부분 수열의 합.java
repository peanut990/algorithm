class Solution {
    public int[] solution(int[] sequence, int k) {
        int leftIdx = 0;
        int rightIdx = 0;
        int sum = sequence[leftIdx];
        int length = Integer.MAX_VALUE;
        int[] answer = new int[2];
        
        while(rightIdx < sequence.length){
            if(sum < k ){
                if(rightIdx + 1 >= sequence.length){
                    break;
                }
                sum += sequence[++rightIdx];
            }else if( sum > k){
                sum -= sequence[leftIdx++];
            }else{
                int curLength = rightIdx - leftIdx + 1;
                if((curLength < length) || (curLength == length && leftIdx < answer[0]) ) {
                    length = curLength;
                    answer = new int[]{leftIdx, rightIdx};
                }
                
                sum -= sequence[leftIdx++];
            }
        }
        return answer;
    }
}