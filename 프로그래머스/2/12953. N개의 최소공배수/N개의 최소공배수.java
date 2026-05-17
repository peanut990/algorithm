import java.util.*;
class Solution {
    public int solution(int[] arr) {
        int res = arr[0];
        
        for(int i = 1;i<arr.length;i++){
            res = cal(res,arr[i]);
        }
        return res;
    }
    
    public int cal(int a, int b){
        int curA = a;
        int curB = b;
        int aM = 2;
        int bM = 2;
        while(curA != curB){
            if(curA < curB){
                curA = a * aM++;
            }else{
                curB = b * bM++;
            }
        }
        
        return curA;
    }
}

/*
6 8
6 12 24 30 ..
8 16 24 32 ..
*/