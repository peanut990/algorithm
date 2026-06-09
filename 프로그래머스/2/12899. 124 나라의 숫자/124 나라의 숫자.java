import java.util.*;

class Solution {
    public String solution(int n) {
        StringBuffer sb = new StringBuffer();
        String[] ary = {"4","1","2"};
        
        while(n>0){
            sb.append(ary[n%3]);
            if(n%3==0){
                n /= 3;
                n--;
            }else{
                n /= 3;
            }
        }
        
        sb.reverse();
        
        return sb.toString();
    }
}

/*
10: 41
11: 42
12: 44

3 12 0
  3 0
  1

3 11 2
  3  0
  1
  
8 2
2

144
143

3 20 2
  6 0
  2
*/