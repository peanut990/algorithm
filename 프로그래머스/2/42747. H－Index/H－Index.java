class Solution {
    public int solution(int[] citations) {
        int answer = 0;
        for(int i = 10_000;i>=0;i--){
            int count = 0;
            for(int c : citations){
                if(c >= i){
                    count++;
                }
            }
            if(count >= i) {
                answer = i;
                break;
            }
        }
        
        return answer; 
    }
}


/*
0 1 3 3 5 6

*/