import java.util.*;

class Solution {
    public long solution(int[] weights) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int w: weights){
            map.put(w, map.getOrDefault(w,0)+1);
        }
        
        long answer = 0;
        
        for(int w: weights){

            for(int i = 2;i<=4;i++){
                int me = w*i;
                for(int j = 2;j<=4;j++){
                    if(i==j || (me %j != 0)) continue;
                    int avail = me/j;

                    answer += map.getOrDefault(avail,0);
                }

            }
            answer += map.get(w) - 1;
        }
        
        return answer/2;
    }
}

/*
100: 200 300 400
180: 360 540 720
360: 720 1080 ...

180 * 3
360 * 2

*/