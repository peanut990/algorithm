class Solution
{
    public int solution(int [][]board)
    {
        int max = 0;
        for(int i = 0;i< board.length;i++){
            for(int j = 0;j<board[0].length;j++){
                if(board[i][j] == 0) continue;
                
                if(i == 0 || j == 0 ) {
                    max = Math.max(max,1);
                    continue;
                }
                
                board[i][j] = Math.min(board[i-1][j], Math.min(board[i][j-1],board[i-1][j-1]))+1;
                max = Math.max(max,board[i][j]);
            }
        }
        
        return max*max;
    }
}