import java.util.*;

class Solution {
    public int[] dirY = {-1,1,0,0}; // 0:상, 1:하, 2:좌, 3:우
    public int[] dirX = {0,0,-1,1};
    
    public final int STRATIGHT_COST = 100;
    public final int CORNER_COST = 500;
    
    public int solution(int[][] board) {
        Queue<int[]> q = new LinkedList<>();
        int[][][] costBoard = new int[board.length][board.length][4];
       
        // 0,0 초기화
        for(int d = 0;d<dirY.length;d++){
            int nextY = 0 + dirY[d];
            int nextX = 0 + dirX[d];

            if(!inBound(nextY, nextX, board) || board[nextY][nextX] == 1) continue;
            
             q.offer(new int[]{nextY,nextX}); // 0:y좌표, 1:x좌표
             costBoard[nextY][nextX][d] = STRATIGHT_COST;
        }
        
        while(!q.isEmpty()){
            int[] poll = q.poll();
            
            // if(poll[2] > board[poll[0]][poll[1]]) continue;
            
            for(int curD = 0;curD< costBoard[0][0].length;curD++){
                if(costBoard[poll[0]][poll[1]][curD] == 0) continue;
                
                int curCost = costBoard[poll[0]][poll[1]][curD];
                
                for(int d = 0;d<dirY.length;d++){
                    int nextY = poll[0] + dirY[d];
                    int nextX = poll[1] + dirX[d];

                    if(!inBound(nextY, nextX, board) || board[nextY][nextX] == 1) continue;

                    // 요금 계산
                    int nextCost = curCost + STRATIGHT_COST;

                    if(curD != d){ // 코너인 경우
                        nextCost += CORNER_COST;
                    }

                    
                    if(costBoard[nextY][nextX][d] != 0 && costBoard[nextY][nextX][d] <= nextCost){
                        continue;
                    }
                    
                    costBoard[nextY][nextX][d] = nextCost;
                    q.offer(new int[]{nextY,nextX});
                        
                }
            }
            
        }    
        
        // for(int i = 0;i<board.length;i++){
        //     for( int j = 0;j<board[0].length;j++){
        //         System.out.printf("%4d ",board[i][j]);
        //     }
        //     System.out.println();
        // }
        int min = Integer.MAX_VALUE;
        for(int c : costBoard[board.length-1][board.length-1]){
            if(c == 0) continue;
            min = Math.min(min,c);
        }
        return min;
    }
    
    public boolean inBound(int y, int x, int[][] board){
        return y < board.length && y >= 0 && x < board[0].length && x>= 0;
    }
}