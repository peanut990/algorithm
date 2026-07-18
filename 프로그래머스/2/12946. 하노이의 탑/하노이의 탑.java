import java.util.*;

class Solution {
    public static List<int[]> li = new ArrayList<>();
    
    public int[][] solution(int n) {    
        rec(n, 1, 3, 2);
        int[][] answer = new int[li.size()][2];
         for (int i = 0; i < li.size(); i++) {
            answer[i] = li.get(i);
        }
        return answer;
    }
    
    public void rec(int n, int from, int to, int via){
        if(n==1) {
            li.add(new int[]{from, to});
            return;
        }
        rec(n-1, from, via, to);
        li.add(new int[]{from, to});
        rec(n-1, via, to, from);
    }
}

/*
123
23       1   [1,3]
3    2   1   [1,2]

3    12      [3,2]
     12  3   [1,3]
1    2   3   [2,1]
1        23  [2,3]
         123 [1,3]
---    
1234
234  1       [1,2]
34   1   2   [1,3]
34       12  [2,3]
4    3   12  
14   3    2
14   23   

4    123
     123  4
     23   14
2    3    14
12   3    4
12        34
2    1    34
     1    234
          1234

----
12345
2345        1
345    2    1
345    12  
45     12   3
145    2    3
145         23
45          123
5      4    123
5      14   23
25     14   3
125    4    3
125    34   
25     34   1
5      234  1

5      1234
       1234 5



*/