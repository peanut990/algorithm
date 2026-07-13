class Solution {
    public int solution(String[] board) {
        int[] counts = new int[2];
        for(int i = 0;i<board.length;i++){
            for(int j = 0;j<board[i].length();j++){
                if(board[i].charAt(j) == 'O') {
                    counts[0]++;
                }else if(board[i].charAt(j) == 'X'){
                    counts[1]++;
                }
            }
        }
        
        if(counts[0] == counts[1]){
            if(check(board,'O')) return 1;
            else return 0;
        }else{
            // -> x가 더 많은 경우 실패
            // -> 2개 이상 차이나는 경우 실패
            if(counts[1] > counts[0]) return 0;
            else if( Math.abs(counts[0] - counts[1]) >= 2) return 0;
            else if( !check(board,'X')) return 0;
            return 1;
        }
    }
        
    public boolean check(String[] board, char target){
       //O가 가로,세로,대각선 완성되어있는 경우 실패
        boolean found = true;
        //가로 체크
        for(int i = 0;i<board.length;i++){
            int oCount = 0;
            for(int j = 0;j<board[i].length();j++){
                if(board[i].charAt(j) == target) {
                    oCount++;
                }
            }
            if(oCount == 3) {
                return false;
            }
         }

        //세로 체크
        for(int i = 0;i<board[0].length();i++){
            int oCount = 0;
            for(int j = 0;j<board.length;j++){
                if(board[j].charAt(i) == target) {
                    oCount++;
                }
            }
            if(oCount == 3) {
                return false;
            }
         }

        //대각 체크
        int oCount = 0;
        for(int i = 0;i<board.length;i++){
            if(board[i].charAt(i) == target) {
                oCount++;
            }
            
            if(oCount == 3) {
                return false;
            }
        }
        
        //대각 체크
        oCount = 0;
        for(int i = 0;i<board.length;i++){
            if(board[i].charAt(board.length-i-1) == target) {
                oCount++;
            }
            
            if(oCount == 3) {
                return false;
            }
        }
        
        return true;
    }
}

/*
개수가 같은 경우
-> O가 가로,세로,대각선 완성되어있는 경우 실패



개수가 다른 경우
-> x가 더 많은 경우 실패
-> 2개 이상 차이나는 경우 실패
-> x가 완성된 경우


0 0 0
x x 0
x x 0


0 x 0
x   x
0 x 0
*/