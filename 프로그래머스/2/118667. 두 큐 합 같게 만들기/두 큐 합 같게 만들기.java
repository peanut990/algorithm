import java.util.*;

class Solution {
    public int solution(int[] queue1, int[] queue2) {
        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();
        
        long sum1 = 0;
        long sum2 = 0;
        for(int i: queue1) {
            sum1 += (long)i;
            q1.add(i);
        }
        for(int i: queue2) {
            sum2 += (long)i;
            q2.add(i);
        }
        
        int count = 0;
        boolean found = false;
        while(count < 2 * (queue1.length + queue2.length)){
            if(sum1 > sum2){
                int poll = q1.poll();
                sum1 -= poll;
                sum2 += poll;
                q2.add(poll);
            }else if(sum1 < sum2){
                int poll = q2.poll();
                sum2 -= poll;
                sum1 += poll;
                q1.add(poll);
            }else{
                found = true;
                break;
            }
            count++;
        }
        
        if(found) return count;
        else return -1;
    }
}

/*
1 2 1 2: 6
1 10 1 2: 14

1 2 1 2 1: 7
10 1 2: 13

1 2 1 2 1 10: 17
1 2: 3

2 1 2 1 10: 16
1 2 1: 4

1 2 1 10: 14
1 2 1 2: 6

2 1 10: 13
1 2 1 2: 7

1 10: 11
1 2 1 2 2: 9

10: 10
1 2 1 2 2 1: 10
---
1 1 : 2
1 2 : 3

1 1 1: 3
2 : 2

11 : 2
21 : 3

112: 4
1 : 1

12 : 3
11 : 2

*/