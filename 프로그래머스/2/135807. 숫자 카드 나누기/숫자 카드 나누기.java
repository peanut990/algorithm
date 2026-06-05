class Solution {
    public int solution(int[] arrayA, int[] arrayB) {
        int answer = 0;
        int gcdA = arrayA[0];
        for(int i = 1;i< arrayA.length;i++){
            gcdA = gcd(gcdA, arrayA[i]);
        }
        
        boolean flag = true;
        for(int i = 0;i< arrayB.length;i++){
            if(arrayB[i] % gcdA == 0 ){
                flag = false;
                break;
            }
        }
        
        if(flag){
            answer = gcdA;
        }
        
        int gcdB = arrayB[0];
        for(int i = 1;i< arrayB.length;i++){
            gcdB = gcd(gcdB, arrayB[i]);
        }
        
        flag = true;
        for(int i = 0;i< arrayA.length;i++){
            if(arrayA[i] % gcdB == 0 ){
                flag = false;
                break;
            }
        }
        
        if(flag){
            answer = Math.max(answer, gcdB);
        }
        
        return answer;
    }
    
    public int gcd(int a, int b){
        int l = Math.max(a,b);
        int r = Math.min(a,b);
        while( r != 0){
            int tmp = r;
            r = l % r;
            l = tmp;
        }
        return l;
    }
}

/*
30 18
18 12
12 6
6 0

3
2
1
*/