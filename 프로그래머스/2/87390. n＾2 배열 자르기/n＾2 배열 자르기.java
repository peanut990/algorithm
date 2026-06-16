import java.util.*;

class Solution {
    public int[] solution(int n, long left, long right) {
        int[] ary = new int[(int)(right - left) + 1];
        int count = 0;
        for(long l = left; l<= right;l++){
            int row = (int)(l /n);
            int col = (int)(l %n);
            
            ary[count++] = Math.max(row, col) + 1;
        }
        
        return ary;
    }
}

/*
1 2 3
2 2 3
3 3 3


0 1 2
3 4 5
6 7 8

행 : i/n
열 : i%n
*/