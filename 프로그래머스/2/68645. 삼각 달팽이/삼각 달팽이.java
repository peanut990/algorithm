import java.util.*;

class Solution {
    public int[] solution(int n) {
        int[][] ary = new int[n][n];
        for(int i =0;i<n;i++){
            Arrays.fill(ary[i],-1);
        }
        
        //init
        for(int i = 0;i<n;i++){
            for(int j = 0;j<=i;j++){
                ary[i][j] = 0;
            }
        }
    
        int num = 1;
        int y = 0;
        int x = 0;
        while(inBound(y,x,n) && ary[y][x] == 0){
            while(inBound(y,x,n) && ary[y][x] == 0){
                ary[y][x] = num++;
                y++;
            }
            
            y--;
            x++;
            while(inBound(y,x,n) && ary[y][x] == 0){
                ary[y][x] = num++;
                x++;
            }
            
            x--;
            while(inBound(y-1,x-1,n) &&  ary[y-1][x-1] == 0){
                ary[y-1][x-1] = num++;
                x--;
                y--;
            }
            
            y++;
        }
        
        List<Integer> li = new ArrayList<>();
        for(int i =0;i<n;i++){
            for(int j = 0;j<n;j++){
                if(ary[i][j] > 0){
                    li.add(ary[i][j]);
                }
            }
        }
        
        int[] answer = new int[li.size()];
        for(int i =0;i<li.size();i++){
            answer[i] = li.get(i);
        }
        return answer;
    }
    
    public boolean inBound(int y, int x, int n){
        return y >= 0 && y < n && x >= 0 && x < n;
    }
}


/*
1
2 9
3 10 8
4 5 6 7

*/