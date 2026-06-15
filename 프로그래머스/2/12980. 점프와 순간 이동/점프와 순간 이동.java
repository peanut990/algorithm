import java.util.*;

public class Solution {
    public int solution(int n) {
        int count = 0;
        while(n > 0){
            if(n%2 == 0){
                n /= 2;
            }else{
                n--;
                count++;
            }
        }
        return count;
    }
}
/*
N: 5
0 1 2 3 4 5 6
  1 2 3 4 5 6
    1 2 3 4 5
        1 2 3
            2
      


*/