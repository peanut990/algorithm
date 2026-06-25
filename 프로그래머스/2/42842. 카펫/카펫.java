class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        for(int l = 1;l<= yellow;l++){
            int c = 0;
            if( yellow % l == 0){
                c = yellow / l;
            }
            
            if((l + c) == brown/2 - 2){
                answer[0] = c + 2;
                answer[1] = l + 2;
                break;
            }
        }
        return answer;
    }
}

/*
b b b b b b b b
b y y y y y y              
b
b
b
b b b b b b b b
y: 6 * 4
b: 8 * 2 + 4 * 2


y -> 행 * 열 = y
b -> (행 + 2) + 열 = b / 2
   -> 행 + 열 = b/2 - 2;
   
   
*/