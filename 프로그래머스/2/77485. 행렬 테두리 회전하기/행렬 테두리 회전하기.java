class Solution {
    int[] dirY = {0,1,0,-1};
    int[] dirX = {1,0,-1,0};
    
    public int[] solution(int rows, int columns, int[][] queries) {
        int[][] ary = new int[rows][columns];
        int num = 1;
        int[] answer = new int[queries.length];
        for(int i = 0;i< rows;i++){
            for(int j =0;j<columns;j++){
                ary[i][j] = num++;
            }
        }
        
        for(int i = 0;i< queries.length;i++){
            answer[i] = rot(ary, queries[i]);
        }
        
        
        return answer;
    }
    
    public int rot(int[][] ary, int[] query){
        int minY = query[0]-1;
        int minX = query[1]-1;
        int maxY = query[2]-1;
        int maxX = query[3]-1;
        
        int dir = 0;
        int curNum = ary[minY][minX];
        int nextY = minY;
        int nextX = minX;
        int min = curNum;
        
        while(true){
            if(dir >= dirY.length) break;
            if(!inBound(minX,minY,maxX,maxY,nextX + dirX[dir],nextY + dirY[dir])){
                dir++;
                continue;
            }
            
            nextY += dirY[dir];
            nextX += dirX[dir];
            
            int tmp = curNum;
            curNum = ary[nextY][nextX];
            ary[nextY][nextX] = tmp;
            
            min = Math.min(min, curNum);
            if(nextY == minY && nextX == minX) break;
        }
        return min;
    }
    
    public boolean inBound(int minX, int minY, int maxX, int maxY, int x, int y){
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
